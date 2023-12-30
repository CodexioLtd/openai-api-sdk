package bg.codexio.ai.openai.api.examples.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.Speaker;
import bg.codexio.ai.openai.api.sdk.voice.speech.Speech;

import java.io.File;
import java.io.IOException;

public class SpeechConfiguredExample {
    public static void main(String[] args) throws IOException {
        var targetFolder = new File(
                SpeechConfiguredExample.class.getClassLoader()
                                             .getResource("")
                                             .getPath() + "generated-audio");

        Speech.defaults()
              .and()
              .hdPowered()
              .voiceOf(Speaker.NOVA)
              .mp3()
              .sameSpeed()
              .immediate()
              .dictate("There is nothing better that the combination of Java,"
                               + " coffee and the company of cats.")
              .downloadTo(targetFolder);
    }
}
