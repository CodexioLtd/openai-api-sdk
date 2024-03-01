package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.file.FileConfigurationStage;

public class FileDownloadingRuntimeSelectionStage<O extends Mergeable<O>>
        extends FileConfigurationStage<O>
        implements RuntimeSelectionStage {
    FileDownloadingRuntimeSelectionStage(
            DefaultOpenAIHttpExecutor<UploadFileRequest, O> executor,
            UploadFileRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    @Override
    public RuntimeExecutor immediate() {
        return null;
    }

    @Override
    public RuntimeExecutor async() {
        return null;
    }

    @Override
    public RuntimeExecutor reactive() {
        return null;
    }
}
