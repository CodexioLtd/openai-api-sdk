package bg.codexio.ai.openai.api.examples.voice.transcription;

import bg.codexio.ai.openai.api.sdk.voice.transcription.Transcription;

import java.io.File;

public class TranscriptionConfiguredExample {
    public static void main(String[] args) {
        var audioFile =
                new File(TranscriptionConfiguredExample.class.getClassLoader()
                                                                     .getResource("audio.mp3")
                                                                     .getPath());
        var response = Transcription.defaults()
                                    .and()
                                    .poweredByWhisper10()
                                    .transcribe(audioFile)
                                    .randomized()
                                    .talkingIn("en")
                                    .subtitlesWithMetadata()
                                    .immediate()
                                    .guide("Be creative")
                                    .text();

        System.out.println(response);
    }
}
