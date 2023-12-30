package bg.codexio.ai.openai.api.payload.voice.request;

import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;

import java.util.Arrays;
import java.util.function.Function;

public enum TranscriptionFormat {
    JSON(
            "json",
            true
    ),
    TEXT(
            "text",
            false
    ),
    SUBTITLES_WITHOUT_METADATA(
            "srt",
            false
    ),
    SUBTITLES_WITH_METADATA(
            "vtt",
            false
    );

    private final String format;
    private final boolean useDefault;

    TranscriptionFormat(
            String format,
            boolean useDefault
    ) {
        this.format = format;
        this.useDefault = useDefault;
    }

    public static TranscriptionFormat fromFormat(String format) {
        return Arrays.stream(TranscriptionFormat.values())
                     .filter(x -> format != null)
                     .filter(x -> x.val()
                                   .equalsIgnoreCase(format))
                     .findFirst()
                     .orElse(TranscriptionFormat.JSON);

    }

    public String val() {
        return this.format;
    }

    public SpeechTextResponse transform(
            String response,
            Function<String, SpeechTextResponse> defaultTransformer
    ) {
        if (defaultTransformer != null && this.useDefault) {
            return defaultTransformer.apply(response);
        }

        return this.doTransform(response);
    }

    SpeechTextResponse doTransform(String response) {
        return new SpeechTextResponse(response);
    }

}
