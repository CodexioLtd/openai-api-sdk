package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.Files;

import java.io.File;

public class DownloadFileAsync {

    public static void main(String[] args) {
        var targetFolder = new File(
                DownloadFileWithFileResult.class.getClassLoader()
                                                .getResource("")
                                                .getPath() + "generated-files");

        Files.defaults()
             .and()
             .download("file-zR7aSAvw1xFBjqLIGKnRpT1q")
             .as("file.py")
             .async()
             .toFolder(targetFolder)
             .standart()
             .whenDownloaded(file -> System.out.println(file.getName()));
    }
}
