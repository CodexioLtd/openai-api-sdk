package bg.codexio.ai.openai.api.examples.images.edit;

import bg.codexio.ai.openai.api.sdk.images.Images;

import java.io.File;

public class ImagesEditCustomMaskFile {
    public static void main(String[] args) {
        var imageToEdit =
                new File(ImagesEditCustomMaskFile.class.getClassLoader()
                                                                 .getResource("editable-image.png")
                                                                 .getPath());

        var imageMask = new File(ImagesEditCustomMaskFile.class.getClassLoader()
                                                               .getResource(
                                                                       "editable-image-mask.png")
                                                               .getPath());

        var response = Images.defaults()
                             .and()
                             .editingStandart(imageToEdit)
                             .masked(imageMask)
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
