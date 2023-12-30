package bg.codexio.ai.openai.api.examples.images.edit;

import bg.codexio.ai.openai.api.sdk.images.Images;
import bg.codexio.ai.openai.api.sdk.images.tolerance.ColorDeviation;

import java.io.File;

public class ImagesEditMaskByHexColor {
    public static void main(String[] args) {
        var inputImage =
                new File(ImagesEditMaskByHexColor.class.getClassLoader()
                                                                .getResource(
                                                                        "editable-image.png")
                                                                .getPath());

        var response = Images.defaults()
                             .and()
                             .editing(inputImage)
                             .masked(
                                     "#6F4E00",
                                     ColorDeviation.BALANCED_TOLERANCE
                             )
                             .singleChoice()
                             .mediumSquare()
                             .expectUrl()
                             .andRespond()
                             .immediate()
                             .generate("Black fur")
                             .andGetRaw();

        System.out.println(response);
    }
}
