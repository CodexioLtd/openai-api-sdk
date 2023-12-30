package bg.codexio.ai.openai.api.examples.images.create;

import bg.codexio.ai.openai.api.sdk.images.Images;

import java.io.File;
import java.io.IOException;

public class ImagesGenerateDalle3CustomSettingsAndDownload {
    public static void main(String[] args) throws IOException {
        var file = new File(
                ImagesGenerateDalle3CustomSettingsAndDownload.class.getClassLoader()
                                                                   .getResource("")
                                                                   .getPath()
                        + "generated-images");

        Images.defaults()
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectUrl()
              .andRespond()
              .immediate()
              .generate("Cat drinking coffee in a green and snowy forest in a"
                                + " beautiful day")
              .andDownload(file);
    }
}
