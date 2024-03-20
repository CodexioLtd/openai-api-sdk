package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.payload.FileContentProvider;

import java.io.File;
import java.io.IOException;

public interface DownloadExecutor {
    File downloadTo(
            File targetFolder,
            FileContentProvider result,
            String format
    ) throws IOException;
}