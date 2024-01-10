package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;

public class UploadFileHttpExecutor
        extends DefaultOpenAIHttpExecutor<UploadFileRequest, FileResponse> {

    private static final String MEDIA_TYPE = "multipart/form-data";
    private static final Class<FileResponse> RESPONSE_TYPE = FileResponse.class;
    private static final String RESOURCE_URI = "/files";

    public UploadFileHttpExecutor(
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
                UploadFileHttpExecutor.class
        );
    }

    public UploadFileHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                UploadFileHttpExecutor.class
        );
    }

    @NotNull
    @Override
    protected Request prepareRequest(UploadFileRequest request) {
        this.reinitializeExecutionIdentification();
        this.trySetMediaType(request);

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

    private void trySetMediaType(UploadFileRequest request) {
        try {
            this.setFormDataMimeType(Files.probeContentType(request.file()
                                                                   .toPath()));
        } catch (IOException e) {
            this.setFormDataMimeType(MEDIA_TYPE);
        }
    }

}