package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageCreationResponse;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

import java.io.File;
import java.util.Arrays;

public class MessageFileState
        extends MessageConfigurationStage {

    MessageFileState(
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

    public MessageCreationResponse feed(String... fileId) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.withFileIds(Arrays.asList(fileId))
                                   .build(),
                this.threadId
        );
    }

    public MessageCreationResponse feed(File file) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.addFileIDs(FileSimplified.simply(file))
                                   .build(),
                this.threadId
        );
    }

    public MessageCreationResponse feed(FileResponse fileResponse) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.addFileIDs(fileResponse.id())
                                   .build(),
                this.threadId
        );
    }

    public MessageMetaStage feedToMetadata(String... fileId) {
        return new MessageMetaStage(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(fileId)),
                this.threadId
        );
    }

    public MessageMetaStage feedToMetadata(File file) {
        return new MessageMetaStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(FileSimplified.simply(file)),
                this.threadId
        );
    }

    public MessageMetaStage feedToMetadata(FileResponse fileResponse) {
        return new MessageMetaStage(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(fileResponse.id()),
                this.threadId
        );
    }

}
