package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.file.response.FileResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;

import java.io.File;
import java.util.Arrays;

public class MessageFileStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {

    MessageFileStage(
            DefaultOpenAIHttpExecutor<MessageRequest, O> httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageAdvancedConfigurationStage<O> feed(String... fileId) {
        return new MessageAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.withFileIds(Arrays.asList(fileId)),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage<O> feed(FileResponse fileResponse) {
        return new MessageAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(fileResponse.id()),
                this.threadId
        );
    }

    public MessageAdvancedConfigurationStage<O> feed(File file) {
        return new MessageAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.addFileIDs(FileSimplified.simply(file)),
                this.threadId
        );
    }
}