package bg.codexio.ai.openai.api.examples.vision;

import bg.codexio.ai.openai.api.examples.images.edit.ImagesEditCustomMaskFile;
import bg.codexio.ai.openai.api.payload.vision.DetailedAnalyze;
import bg.codexio.ai.openai.api.sdk.vision.Vision;

import java.io.File;

public class VisionDetailedExampleWithLimit {
    public static void main(String[] args) {
        var inputImage =
                new File(ImagesEditCustomMaskFile.class.getClassLoader()
                                                                .getResource(
                                                                        "codi-image-to-explain.png")
                                                                .getPath());

        var response = Vision.defaults()
                             .and()
                             .poweredByGpt40Vision()
                             .limitResponseTo(300)
                             .explain(inputImage)
                             .analyze(DetailedAnalyze.HIGH)
                             .andRespond()
                             .immediate()
                             .describeRaw();

        System.out.println(response);
    }
}
