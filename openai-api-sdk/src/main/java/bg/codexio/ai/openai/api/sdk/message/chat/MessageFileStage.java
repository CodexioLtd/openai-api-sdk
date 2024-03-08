package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;

import java.io.File;
import java.util.Arrays;

public class MessageFileStage
        extends MessageConfigurationStage {

    public MessageFileStage(
            MessageHttpExecutor httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageAdvancedConfigurationStage feed(String... fileId) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(fileId)),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage feed(FileResponse fileResponse) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(fileResponse.id()),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage feed(File file) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(FileUploadSimplified.simply(file)),
                this.threadId
        );
    }
}