package bg.codexio.ai.openai.api.examples.voice.speech;

import bg.codexio.ai.openai.api.sdk.voice.speech.Speech;

import java.io.File;
import java.io.IOException;

public class SpeechSimpleExample {
    public static void main(String[] args) throws IOException {
        var targetFolder = new File(SpeechSimpleExample.class.getClassLoader()
                                                             .getResource("")
                                                             .getPath()
                                            + "generated-audio");

        Speech.simply()
              .dictate("Katzen sind wunderschöne Geschöpfe.")
              .downloadTo(targetFolder);
    }
}
