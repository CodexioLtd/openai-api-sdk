package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class UploadFileAsync {

    public static void main(String[] args) {
        var file = new File(UploadFileImmediate.class.getClassLoader()
                                                     .getResource("fake-file.txt")
                                                     .getPath());

        Files.defaults()
             .and()
             .upload()
             .forAssistants()
             .async()
             .feed(file)
             .then(response -> System.out.println(response.id()));
    }
}