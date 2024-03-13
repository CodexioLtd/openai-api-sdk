package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class UploadFileReactive {

    public static void main(String[] args) {
        var file = new File(UploadFileImmediate.class.getClassLoader()
                                                     .getResource("fake-file.txt")
                                                     .getPath());

        Files.defaults()
             .and()
             .upload()
             .forAssistants()
             .reactive()
             .feedRaw(file)
             .response()
             .subscribe(response -> System.out.println(response.id()));
    }
}