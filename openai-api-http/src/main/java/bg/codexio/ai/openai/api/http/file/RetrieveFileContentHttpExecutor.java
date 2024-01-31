package bg.codexio.ai.openai.api.http.file;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class RetrieveFileContentHttpExecutor
        extends DefaultOpenAIHttpExecutor<UploadFileRequest,
        FileContentResponse> {

    private static final Class<FileContentResponse> RESPONSE_TYPE =
            FileContentResponse.class;
    private static final String RESOURCE_URI = "/files/%s/content";

    public RetrieveFileContentHttpExecutor(
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
                RetrieveFileContentHttpExecutor.class
        );
    }

    public RetrieveFileContentHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                RetrieveFileContentHttpExecutor.class
        );
    }

    @Override
    protected FileContentResponse toResponse(Response response)
            throws IOException {
        return new FileContentResponse(response.body()
                                               .bytes());
    }
}