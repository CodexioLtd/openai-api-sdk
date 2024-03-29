package bg.codexio.ai.openai.api.examples.file;


import bg.codexio.ai.openai.api.payload.file.purpose.AssistantPurpose;
import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class UploadFile {

    public static void main(String[] args) {
        var file = new File(UploadFile.class.getClassLoader()
                                            .getResource("fake-file.txt")
                                            .getPath());
        var fileId = Files.defaults()
                          .and()
                          .upload()
                          .targeting(new AssistantPurpose())
                          .feed(file);

        System.out.println(fileId);
    }
}
