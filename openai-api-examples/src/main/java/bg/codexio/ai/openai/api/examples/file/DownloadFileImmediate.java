package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;
import java.io.IOException;

public class DownloadFileImmediate {

    public static void main(String[] args) throws IOException {
        var file = new File(DownloadFileImmediate.class.getClassLoader()
                                                       .getResource("")
                                                       .getPath()
                                    + "generated-files");

        var downloadedFile = Files.defaults()
                                  .and()
                                  .download("file-zR7aSAvw1xFBjqLIGKnRpT1q")
                                  .as("file.py")
                                  .immediate()
                                  .standart()
                                  .toFolder(file);

        System.out.println(downloadedFile.getAbsoluteFile()
                                         .getName());
    }
}