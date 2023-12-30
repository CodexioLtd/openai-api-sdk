package bg.codexio.ai.openai.api.http.voice;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionFormat;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Implementation for Translations API
 */
public class TranslationHttpExecutor
        extends DefaultOpenAIHttpExecutor<TranslationRequest,
        SpeechTextResponse> {

    private static final String MEDIA_TYPE = "audio/mpeg";

    private static final Class<SpeechTextResponse> RESPONSE_TYPE =
            SpeechTextResponse.class;
    private static final String RESOURCE_URI = "/audio/translations";

    private String format;


    public TranslationHttpExecutor(
            OkHttpClient client,
            String baseUrl,
            ObjectMapper objectMapper
    ) {
        super(
                client,
                baseUrl,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                TranslationHttpExecutor.class
        );
    }

    public TranslationHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                TranslationHttpExecutor.class
        );
    }

    @NotNull
    @Override
    protected Request prepareRequest(TranslationRequest request) {
        this.reinitializeExecutionIdentification();
        this.trySetMediaType(request);
        this.format = request.responseFormat();

        log(
                "Incoming request to {}{} with body: {}",
                this.baseUrl,
                this.resourceUri,
                request
        );

        return new Request.Builder().url(this.baseUrl + this.resourceUri)
                                    .post(this.toFormData(request))
                                    .build();
    }

    private void trySetMediaType(TranslationRequest request) {
        try {
            this.setFormDataMimeType(Files.probeContentType(request.file()
                                                                   .toPath()));
        } catch (IOException e) {
            this.setFormDataMimeType(MEDIA_TYPE);
        }
    }

    @Override
    protected SpeechTextResponse toResponse(String response) {
        return TranscriptionFormat.fromFormat(this.format)
                                  .transform(
                                          response,
                                          super::toResponse
                                  );
    }

    @Override
    protected void setMultipartBoundary(String boundary) {
        super.setMultipartBoundary(boundary);
    }
}
