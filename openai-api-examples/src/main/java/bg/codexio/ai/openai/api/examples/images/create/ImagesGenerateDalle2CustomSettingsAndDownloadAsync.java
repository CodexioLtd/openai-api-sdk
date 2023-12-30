package bg.codexio.ai.openai.api.examples.images.create;

import bg.codexio.ai.openai.api.sdk.images.Images;

import java.io.File;
import java.io.IOException;

public class ImagesGenerateDalle2CustomSettingsAndDownloadAsync {
    public static void main(String[] args) throws IOException {
        var file = new File(
                ImagesGenerateDalle2CustomSettingsAndDownloadAsync.class.getClassLoader()
                                                                        .getResource("")
                                                                        .getPath()
                        + "generated-images");

        Images.defaults()
              .and()
              .creating()
              .poweredByDallE2()
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .async()
              .generate("Cartoon cat")
              .whenDownloaded(
                      file,
                      System.out::println
              );
    }
}
