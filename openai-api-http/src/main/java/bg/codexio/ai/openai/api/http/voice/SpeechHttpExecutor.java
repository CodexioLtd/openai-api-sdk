package bg.codexio.ai.openai.api.http.voice;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.payload.voice.response.AudioBinaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

/**
 * Implementation for Text-To-Speech API
 */
public class SpeechHttpExecutor
        extends DefaultOpenAIHttpExecutor<SpeechRequest, AudioBinaryResponse> {

    private static final Class<AudioBinaryResponse> RESPONSE_TYPE =
            AudioBinaryResponse.class;
    private static final String RESOURCE_URI = "/audio/speech";

    public SpeechHttpExecutor(
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
                SpeechHttpExecutor.class
        );
    }

    public SpeechHttpExecutor(
            HttpExecutorContext context,
            ObjectMapper objectMapper
    ) {
        super(
                context,
                objectMapper,
                RESPONSE_TYPE,
                RESOURCE_URI,
                true,
                SpeechHttpExecutor.class
        );
    }


    @Override
    protected AudioBinaryResponse toResponse(Response response)
            throws IOException {
        return new AudioBinaryResponse(response.body()
                                               .bytes());
    }

    @Override
    protected AudioBinaryResponse toResponse(String response) {
        return new AudioBinaryResponse(response.getBytes());
    }
}
