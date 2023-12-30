package bg.codexio.ai.openai.api.examples.images.create;

import bg.codexio.ai.openai.api.sdk.images.Images;

public class ImagesSimplyGenerate {
    public static void main(String[] args) {
        var response = Images.simply()
                             .generate("Image of a cat playing in the snow");

        System.out.println(response);
    }
}
