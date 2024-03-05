package bg.codexio.ai.openai.api.examples.file;

import bg.codexio.ai.openai.api.sdk.file.FileResult;

import java.io.File;
import java.io.IOException;

public class DownloadFileWithFileResult {

    public static void main(String[] args) throws IOException {
        var targetFolder = new File(
                DownloadFileWithFileResult.class.getClassLoader()
                                                .getResource("")
                                                .getPath() + "generated-files");

        var downloadedFile = new FileResult(
                "file-zR7aSAvw1xFBjqLIGKnRpT1q",
                "file.py"
        ).downloadImmediate(targetFolder);

        System.out.println(downloadedFile.getName());
    }
}
