package bg.codexio.ai.openai.api.examples.file;


import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class UploadFileImmediate {

    public static void main(String[] args) {
        var file = new File(UploadFileImmediate.class.getClassLoader()
                                                     .getResource("fake-file.txt")
                                                     .getPath());
        var fileId = Files.defaults()
                          .and()
                          .upload()
                          .targeting(new AssistantPurpose())
                          .immediate()
                          .feed(file);

        System.out.println(fileId);
    }
}
