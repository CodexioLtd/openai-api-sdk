package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class SynchronousPromptStage
        extends TranscriptionConfigurationStage
        implements RuntimeExecutor {

    SynchronousPromptStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Guides the transcription with additional prompt
     *
     * @return {@link SpeechTextResponse} the response from the API in the
     * configured format
     */
    public SpeechTextResponse guide(String prompt) {
        return this.executor.execute(this.requestBuilder.withPrompt(prompt)
                                                        .build());
    }

    /**
     * Sends request to OpenAI API without additional prompt
     *
     * @return {@link SpeechTextResponse} the response from the API in the
     * configured format
     */
    public SpeechTextResponse unguided() {
        return this.guide(null);
    }
}
