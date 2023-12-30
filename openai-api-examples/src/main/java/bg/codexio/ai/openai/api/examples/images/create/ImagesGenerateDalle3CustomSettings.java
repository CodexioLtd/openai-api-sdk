package bg.codexio.ai.openai.api.examples.images.create;

import bg.codexio.ai.openai.api.sdk.images.Images;

public class ImagesGenerateDalle3CustomSettings {
    public static void main(String[] args) {
        var response = Images.defaults()
                             .and()
                             .creating()
                             .poweredByDallE3()
                             .vivid()
                             .highDefinitioned()
                             .portrait()
                             .expectUrl()
                             .andRespond()
                             .immediate()
                             .generate("Cat drinking coffee in a green and "
                                               + "snowy forest in a beautiful"
                                               + " day")
                             .andGetRaw();

        System.out.println(response);
    }
}
