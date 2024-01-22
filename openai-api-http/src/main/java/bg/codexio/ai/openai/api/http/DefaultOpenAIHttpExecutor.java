package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.http.exception.HttpCallFailedException;
import bg.codexio.ai.openai.api.http.exception.OpenAIRespondedNot2xxException;
import bg.codexio.ai.openai.api.http.exception.UnparseableRequestException;
import bg.codexio.ai.openai.api.http.exception.UnparseableResponseException;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables;
import bg.codexio.ai.openai.api.payload.error.ErrorResponseHolder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * <p>
 * Common implementation that should work out of the box
 * when inherited with the correct input and output models.
 * </p>
 * {@inheritDoc}
 */
public abstract class DefaultOpenAIHttpExecutor<I extends Streamable,
        O extends Mergeable<O>>
        implements OpenAIHttpExecutor<I, O> {


    /**
     * Default for the Content-Type header
     */
    protected static final MediaType DEFAULT_MEDIA_TYPE = MediaType.get(
            "application/json");

    /**
     * Pattern mostly used for non-Jackson conversions,
     * such as multipart form data.
     */
    private static final String CAMEL_TO_SNAKE_CASE_PATTERN = "([a-z])([A-Z]+)";
    protected final OkHttpClient client;
    protected final String baseUrl;
    protected final ObjectMapper objectMapper;
    protected final Class<O> responseType;
    protected final String resourceUri;
    protected final boolean streamable;
    /**
     * Main logger instance.
     * Can be simplified with @Slf4j
     * if Lombok is introduced.
     */
    private final Logger log;
    /**
     * Usually used for logging purposes as a trace-id
     */
    private String currentExecutionIdentifier;

    /**
     * Default mime type for uploaded files.
     * It will default to <strong>application/octet-stream</strong>
     * if this property is not set.
     * For images, for instance, it can be image/png
     */
    private String formDataMimeType;

    /**
     * Default boundary header for multipart uploads.
     * Usually we leave this to null or {@link UUID#randomUUID()},
     * but it can be hardcoded to leverage testing.
     */
    private String multipartBoundary;

    /**
     * @param context      timeouts and other HTTP settings
     * @param objectMapper configured {@link ObjectMapper}
     * @param responseType the type of the response object
     * @param resourceUri  the main prefix for the resource, e.g. /images
     * @param streamable   whether the whole API supports streaming
     * @param currentType  the type of the inheritor to initialize logger with
     */
    protected DefaultOpenAIHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper,
            Class<O> responseType,
            String resourceUri,
            boolean streamable,
            Class<? extends DefaultOpenAIHttpExecutor<I, O>> currentType
    ) {
        this(
                new OkHttpClient.Builder().callTimeout(
                                                  context.timeouts()
                                                         .call()
                                                         .period(),
                                                  context.timeouts()
                                                         .call()
                                                         .timeUnit()
                                          )
                                          .connectTimeout(
                                                  context.timeouts()
                                                         .connect()
                                                         .period(),
                                                  context.timeouts()
                                                         .connect()
                                                         .timeUnit()
                                          )
                                          .readTimeout(
                                                  context.timeouts()
                                                         .read()
                                                         .period(),
                                                  context.timeouts()
                                                         .read()
                                                         .timeUnit()
                                          )
                                          .addInterceptor(new AuthenticationInterceptor(context))
                                          .build(),
                context.credentials()
                       .baseUrl(),
                objectMapper,
                responseType,
                resourceUri,
                streamable,
                currentType
        );
    }

    /**
     * @param client       already configured {@link OkHttpClient} client
     * @param baseUrl      the base url such as
     *                     <a href="https://api.openai.com/v1/">https://api.openai.com/v1/</a>
     * @param objectMapper configured {@link ObjectMapper}
     * @param responseType the type of the response object
     * @param resourceUri  the main prefix for the resource, e.g. /images
     * @param streamable   whether the whole API supports streaming
     * @param currentType  the type of the inheritor to initialize logger with
     */
    protected DefaultOpenAIHttpExecutor(
            OkHttpClient client,
            String baseUrl,
            ObjectMapper objectMapper,
            Class<O> responseType,
            String resourceUri,
            boolean streamable,
            Class<? extends DefaultOpenAIHttpExecutor<I, O>> currentType
    ) {
        this(
                client,
                baseUrl,
                objectMapper,
                responseType,
                resourceUri,
                streamable,
                LoggerFactory.getLogger(currentType)
        );
    }

    /**
     * @param client       already configured {@link OkHttpClient} client
     * @param baseUrl      the base url such as
     *                     <a href="https://api.openai.com/v1/">https://api.openai.com/v1/</a>
     * @param objectMapper configured {@link ObjectMapper}
     * @param responseType the type of the response object
     * @param resourceUri  the main prefix for the resource, e.g. /images
     * @param streamable   whether the whole API supports streaming
     * @param log          {@link Logger} instance configured for the
     *                     particular inheritor
     */
    protected DefaultOpenAIHttpExecutor(
            OkHttpClient client,
            String baseUrl,
            ObjectMapper objectMapper,
            Class<O> responseType,
            String resourceUri,
            boolean streamable,
            Logger log
    ) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
        this.responseType = responseType;
        this.resourceUri = resourceUri;
        this.streamable = streamable;
        this.log = log;

        this.configureObjectMapper();
    }

    protected static <T> T getField(
            Field field,
            Object obj,
            Class<T> type
    ) {
        try {
            return type.cast(field.get(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void configureMappingExternally(Consumer<ObjectMapper> mappingConsumer) {
        mappingConsumer.accept(this.objectMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public O execute(I request) {
        var httpRequest = this.prepareRequest(request);

        return this.performRequestExecution(httpRequest);
    }

    @Override
    public O executeWithPathVariables(String... pathVariables) {
        var httpRequest = this.prepareRequest(pathVariables);

        return this.performRequestExecution(httpRequest);
    }

    @Override
    public O executeWithPathVariable(
            I request,
            String pathVariable
    ) {
        var httpRequest = this.prepareRequestWithPathVariable(
                request,
                pathVariable
        );

        return this.performRequestExecution(httpRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeAsync(
            I request,
            Consumer<String> callBack,
            Consumer<O> finalizer
    ) {
        var httpRequest = prepareRequest(request);

        this.client.newCall(httpRequest)
                   .enqueue(new Callback() {
                       @Override
                       public void onFailure(
                               @NotNull Call call,
                               @NotNull IOException e
                       ) {
                           throw new HttpCallFailedException(
                                   baseUrl + resourceUri,
                                   e
                           );
                       }

                       @Override
                       public void onResponse(
                               @NotNull Call call,
                               @NotNull Response response
                       ) throws IOException {
                           var content = new StringBuilder();
                           var responseContent = new ArrayList<O>();
                           try (var httpResponseBody = response.body()) {
                               throwOnError(response);

                               var reader =
                                       new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
                               var line = (String) null;
                               while ((line = reader.readLine()) != null) {
                                   if (canStream(request)) {
                                       line = line.replace(
                                                          "data:",
                                                          ""
                                                  )
                                                  .trim();
                                       if (line.equals("[DONE]")) {
                                           break;
                                       }
                                   }

                                   callBack.accept(line);
                                   if (canStream(request)) {
                                       responseContent.add(toResponse(line));
                                   } else {
                                       content.append(line);
                                   }
                               }
                           }

                           if (canStream(request)) {
                               finalizer.accept(responseContent.stream()
                                                               .reduce(Mergeable::doMerge)
                                                               .orElse(null));
                           } else {
                               finalizer.accept(toResponse(content.toString()));
                           }
                       }
                   });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReactiveExecution<O> executeReactive(I request) {
        var lines =
                Flux.<String>create(sink -> this.client.newCall(prepareRequest(request))
                                                           .enqueue(new Callback() {
                                                               @Override
                                                               public void onFailure(
                                                                       @NotNull Call call,
                                                                       @NotNull IOException e
                                                               ) {
                                                                   throw new HttpCallFailedException(
                                                                           baseUrl
                                                                                   + resourceUri,
                                                                           e
                                                                   );
                                                               }

                                                               @Override
                                                               public void onResponse(
                                                                       @NotNull Call call,
                                                                       @NotNull Response response
                                                               ) throws
                                                                 IOException {
                                                                   try (var httpResponseBody = response.body()) {
                                                                       try {
                                                                           throwOnError(response);
                                                                       } catch (Throwable throwable) {
                                                                           sink.error(throwable);
                                                                           return;
                                                                       }

                                                                       var reader = new BufferedReader(new InputStreamReader(httpResponseBody.byteStream()));
                                                                       var line = (String) null;
                                                                       while ((line = reader.readLine())
                                                                               != null) {
                                                                           if (canStream(request)) {
                                                                               line = line.replace(
                                                                                                  "data:",
                                                                                                  ""
                                                                                          )
                                                                                          .trim();
                                                                               if (line.equals("[DONE]")) {
                                                                                   break;
                                                                               }
                                                                           }

                                                                           sink.next(line);
                                                                       }
                                                                   } finally {
                                                                       sink.complete();
                                                                   }
                                                               }
                                                           }))
                        .share();

        if (this.canStream(request)) {
            var response = lines.collectList()
                                .mapNotNull(list -> list.stream()
                                                        .filter(Objects::nonNull)
                                                        .filter(Predicate.not(String::isBlank))
                                                        .map(this::toResponse)
                                                        .reduce(Mergeable::doMerge)
                                                        .orElse(null));

            return new ReactiveExecution<>(
                    lines,
                    response
            );
        }

        var response = lines.collectList()
                            .map(line -> String.join(
                                    "",
                                    line
                            ))
                            .map(this::toResponse);

        return new ReactiveExecution<>(
                lines,
                response
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canStream(I input) {
        return this.streamable && input.stream();
    }

    protected void configureObjectMapper() {
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.objectMapper.configure(
                DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                false
        );
        this.objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        );
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected String getEnv(String key) {
        return System.getenv(key);
    }

    protected String toJson(I request) {
        try {
            return this.objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new UnparseableRequestException(e);
        }
    }

    @NotNull
    protected Request prepareRequest(I request) {
        var json = this.performRequestInitialization(
                request,
                this.resourceUri
        );

        return new Request.Builder().url(this.baseUrl + this.resourceUri)
                                    .post(RequestBody.create(
                                            json,
                                            DEFAULT_MEDIA_TYPE
                                    ))
                                    .build();
    }

    protected Request prepareRequest(String... pathVariables) {
        var resourceUriWithPathVariable = String.format(
                this.resourceUri,
                (Object[]) pathVariables
        );

        return new Request.Builder().url(this.baseUrl.concat(resourceUriWithPathVariable))
                                    .get()
                                    .build();
    }

    protected O performRequestExecution(Request httpRequest) {
        try (
                var httpResponse = this.client.newCall(httpRequest)
                                              .execute()
        ) {
            this.throwOnError(httpResponse);

            return this.toResponse(httpResponse);
        } catch (IOException e) {
            throw new HttpCallFailedException(
                    httpRequest.url()
                               .toString(),
                    e
            );
        }
    }

    @NotNull
    protected Request prepareRequestWithPathVariable(
            I request,
            String pathVariable
    ) {
        var resourceUriWithPathVariable = String.format(
                this.resourceUri,
                pathVariable
        );
        var json = this.performRequestInitialization(
                request,
                resourceUriWithPathVariable
        );

        return new Request.Builder().url(this.baseUrl.concat(resourceUriWithPathVariable))
                                    .post(RequestBody.create(
                                            json,
                                            DEFAULT_MEDIA_TYPE
                                    ))
                                    .build();
    }

    protected O toResponse(Response response) throws IOException {
        try {
            var body = response.body()
                               .string();
            log(
                    "Received raw response: {}",
                    body
            );

            return this.toResponse(body);
        } catch (IOException e) {
            throw new HttpCallFailedException(
                    response.request()
                            .url()
                            .toString(),
                    e
            );
        }
    }

    protected O toResponse(String response) {
        try {
            log(
                    "Received raw response: {}",
                    response
            );

            return this.objectMapper.readValue(
                    response,
                    this.responseType
            );
        } catch (Exception e) {
            throw new UnparseableResponseException(
                    response,
                    this.responseType,
                    e
            );
        }
    }

    protected ErrorResponseHolder toError(String response) {
        try {
            log(
                    "Received error response: {}",
                    response
            );

            return this.objectMapper.readValue(
                    response,
                    ErrorResponseHolder.class
            );
        } catch (Exception e) {
            throw new UnparseableResponseException(
                    response,
                    this.responseType,
                    e
            );
        }
    }

    protected String performRequestInitialization(
            I request,
            String resourceUri
    ) {
        reinitializeExecutionIdentification();

        var json = this.toJson(request);

        log(
                "Incoming request to {}{} with body: {}",
                this.baseUrl,
                resourceUri,
                json
        );

        return json;
    }

    protected void log(
            String message,
            Object... args
    ) {
        var enabled =
                this.getEnv(AvailableEnvironmentVariables.OPENAI_LOGGING_ENABLED.name());
        if (enabled == null) {
            return;
        }

        var logMessage =
                "[" + this.currentExecutionIdentifier + "]: " + message;
        var level =
                this.getEnv(AvailableEnvironmentVariables.OPENAI_LOGGING_LEVEL.name());
        if (level == null) {
            log.debug(
                    logMessage,
                    args
            );
            return;
        }

        log.atLevel(Level.valueOf(level.toUpperCase()))
           .log(
                   logMessage,
                   args
           );
    }

    protected void reinitializeExecutionIdentification() {
        this.currentExecutionIdentifier = UUID.randomUUID()
                                              .toString();
    }

    protected MultipartBody toFormData(Object obj) {
        MultipartBody.Builder builder =
                new MultipartBody.Builder(Objects.requireNonNullElse(
                this.multipartBoundary,
                UUID.randomUUID()
                    .toString()
        )).setType(MultipartBody.FORM);
        this.hydrateFormData(
                builder,
                obj,
                new HashSet<>()
        );

        return builder.build();
    }

    protected void hydrateFormData(
            MultipartBody.Builder builder,
            Object obj,
            Set<Object> visited
    ) {
        Arrays.stream(obj.getClass()
                         .getDeclaredFields())
              .filter(Field::trySetAccessible)
              .forEach(field -> {
                  if (File.class.isAssignableFrom(field.getType())) {
                      var file = getField(
                              field,
                              obj,
                              File.class
                      );
                      if (file != null) {
                          builder.addFormDataPart(
                                  this.convertFormDataFieldName(field.getName()),
                                  file.getName(),
                                  RequestBody.create(
                                          file,
                                          MediaType.parse(this.getFormDataMimeType())
                                  )
                          );
                      }
                  } else if (Streamable.class.isAssignableFrom(field.getType())) {
                      var streamable = getField(
                              field,
                              obj,
                              Streamable.class
                      );
                      if (streamable != null && !visited.contains(streamable)) {
                          visited.add(streamable);
                          this.hydrateFormData(
                                  builder,
                                  streamable,
                                  visited
                          );
                      }
                  } else {
                      var value = getField(
                              field,
                              obj,
                              Object.class
                      );
                      if (value != null) {
                          builder.addFormDataPart(
                                  this.convertFormDataFieldName(field.getName()),
                                  value.toString()
                          );
                      }
                  }
              });
    }

    protected String getFormDataMimeType() {
        return Objects.requireNonNullElse(
                this.formDataMimeType,
                "application/octet-stream"
        );
    }

    protected void setFormDataMimeType(String mimeType) {
        this.formDataMimeType = mimeType;
    }

    protected void setMultipartBoundary(String boundary) {
        this.multipartBoundary = boundary;
    }

    protected String convertFormDataFieldName(String originalName) {
        return originalName.replaceAll(
                                   CAMEL_TO_SNAKE_CASE_PATTERN,
                                   "$1_$2"
                           )
                           .toLowerCase();
    }

    protected void throwOnError(Response httpResponse) throws IOException {
        if (httpResponse.code() >= 300) {
            var errorHolder = this.toError(httpResponse.body()
                                                       .string());
            throw new OpenAIRespondedNot2xxException(
                    errorHolder,
                    httpResponse.code()
            );
        }
    }
}