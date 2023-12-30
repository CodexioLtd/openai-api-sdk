package bg.codexio.ai.openai.api.examples.images.variations;

import bg.codexio.ai.openai.api.examples.images.edit.ImagesEditCustomMaskFile;
import bg.codexio.ai.openai.api.sdk.images.Images;

import java.io.File;

public class ImageMultipleVariationsAsync {
    public static void main(String[] args) {
        var inputImage =
                new File(ImagesEditCustomMaskFile.class.getClassLoader()
                                                                .getResource(
                                                                        "image-for-variations.png")
                                                                .getPath());

        Images.defaults()
              .and()
              .another(inputImage)
              .withChoices(3)
              .smallSquare()
              .expectUrl()
              .andRespond()
              .async()
              .onResponse(System.out::println);
    }
}
