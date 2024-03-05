package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.http.file.RetrieveFileContentHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.request.UploadFileRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

import java.io.File;

public class FileDownloadingReactiveContextStage
        extends FileDownloadingConfigurationStage
        implements RuntimeExecutor {

    public FileDownloadingReactiveContextStage(
            RetrieveFileContentHttpExecutor executor,
            UploadFileRequest.Builder requestBuilder,
            FileDownloadingMeta.Builder fileDownloadingMeta
    ) {
        super(
                executor,
                requestBuilder,
                fileDownloadingMeta
        );
    }

    public Mono<File> toFolder(File targetFolder) {
        return this.executor.executeReactiveWithPathVariables(this.fileDownloadingMeta.fileId())
                            .response()
                            .handle((response, sink) -> {
                                try {
                                    sink.next(DownloadExecutor.downloadTo(
                                            targetFolder,
                                            response,
                                            this.fileDownloadingMeta.fileName()
                                    ));
                                } catch (Exception e) {
                                    sink.error(e);
                                }
                            });
    }
}