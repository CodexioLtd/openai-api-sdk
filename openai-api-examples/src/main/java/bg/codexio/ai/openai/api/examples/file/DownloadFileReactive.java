package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class DownloadFileReactive {

    public static void main(String[] args) {
        var file = new File(DownloadFileImmediate.class.getClassLoader()
                                                       .getResource("")
                                                       .getPath()
                                    + "generated-files");

        Files.defaults()
             .and()
             .download("file-zR7aSAvw1xFBjqLIGKnRpT1q")
             .as("file.py")
             .reactive()
             .toFolder(file)
             .subscribe(result -> System.out.println(result.getName()));

    }
}
