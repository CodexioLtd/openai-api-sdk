package bg.codexio.ai.openai.api.examples.voice.translation;

import bg.codexio.ai.openai.api.sdk.voice.translation.Translation;

import java.io.File;

public class TranslationSimpleExample {
    public static void main(String[] args) {
        var audioFile = new File(TranslationSimpleExample.class.getClassLoader()
                                                               .getResource(
                                                                       "audio-de.mp3")
                                                               .getPath());

        var response = Translation.simply()
                                  .translate(audioFile)
                                  .unguided()
                                  .text();

        System.out.println(response);
    }
}
