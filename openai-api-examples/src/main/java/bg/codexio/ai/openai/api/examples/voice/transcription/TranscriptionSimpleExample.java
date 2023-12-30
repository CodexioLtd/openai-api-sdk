package bg.codexio.ai.openai.api.examples.voice.transcription;

import bg.codexio.ai.openai.api.sdk.voice.transcription.Transcription;

import java.io.File;

public class TranscriptionSimpleExample {
    public static void main(String[] args) {
        var audioFile =
                new File(TranscriptionSimpleExample.class.getClassLoader()
                                                                 .getResource("audio.mp3")
                                                                 .getPath());
        var response = Transcription.simply()
                                    .transcribe(audioFile)
                                    .unguided();

        System.out.println(response);
    }
}
