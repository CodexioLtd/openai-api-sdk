package bg.codexio.ai.openai.api.examples.voice.translation;

import bg.codexio.ai.openai.api.sdk.voice.translation.Translation;

import java.io.File;

public class TranslationConfiguredExample {
    public static void main(String[] args) {
        var audioFile =
                new File(TranslationConfiguredExample.class.getClassLoader()
                                                                   .getResource("audio-de.mp3")
                                                                   .getPath());

        var response = Translation.defaults()
                                  .and()
                                  .poweredByWhisper10()
                                  .translate(audioFile)
                                  .deterministic()
                                  .justText()
                                  .immediate()
                                  .unguided()
                                  .text();

        System.out.println(response);
    }
}
