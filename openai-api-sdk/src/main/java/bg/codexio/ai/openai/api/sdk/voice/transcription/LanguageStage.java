package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

/**
 * Tells the OpenAI API in which language
 * the voice in the audio file is.
 */
public class LanguageStage
        extends TranscriptionConfigurationStage {

    LanguageStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Denotes a language in which the audio file is
     *
     * @param language in short format such as <b>en, de, ft, es, bg, lt, ro,
     *                 ...</b>
     * @return {@link FormatStage} to configure the format of the output file
     */
    public FormatStage talkingIn(String language) {
        return new FormatStage(
                this.executor,
                this.requestBuilder.withLanguage(language)
        );
    }

    /**
     * Denotes a language of the audio file to be English (en)
     *
     * @return {@link FormatStage} to configure the format of the output file
     */
    public FormatStage talkingInEnglish() {
        return this.talkingIn("en");
    }

    /**
     * Denotes a language of the audio file to be German (de)
     *
     * @return {@link FormatStage} to configure the format of the output file
     */
    public FormatStage talkingInDeutsch() {
        return this.talkingIn("de");
    }
}
