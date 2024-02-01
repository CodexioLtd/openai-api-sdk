package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.MessageResult;
import bg.codexio.ai.openai.api.payload.message.content.ImageFileContent;
import bg.codexio.ai.openai.api.payload.message.content.MessageContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import java.util.List;
import java.util.Optional;

public class MessageAnswersRetrievalTypeStage {

    private final RetrieveListMessagesHttpExecutor listMessagesHttpExecutor;
    private final String threadId;

    public MessageAnswersRetrievalTypeStage(
            RetrieveListMessagesHttpExecutor listMessagesHttpExecutor,
            String threadId
    ) {
        this.listMessagesHttpExecutor = listMessagesHttpExecutor;
        this.threadId = threadId;
    }

    public ListMessagesResponse answersRaw() {
        return new MessageAnswerStage<>(
                this.listMessagesHttpExecutor,
                null,
                this.threadId
        ).answersRaw();
    }

    public ListMessagesResponse answersRaw(String threadId) {
        return new MessageAnswerStage<>(
                this.listMessagesHttpExecutor,
                null,
                null
        ).answersRaw(threadId);
    }

    public ListMessagesResponse answersRaw(ThreadResponse threadResponse) {
        return new MessageAnswerStage<>(
                this.listMessagesHttpExecutor,
                null,
                null
        ).answersRaw(threadResponse);
    }

    public MessageResult answers() {
        return this.getResult(this.answersRaw()
                                  .data());
    }

    public MessageResult answers(String threadId) {
        return this.getResult(this.answersRaw(threadId)
                                  .data());
    }

    public MessageResult answers(ThreadResponse threadResponse) {
        return this.getResult(this.answersRaw(threadResponse)
                                  .data());
    }

    private MessageResult getResult(List<MessageResponse> data) {
        return Optional.ofNullable(data)
                       .filter(this::hasNonEmptyData)
                       .map(this::getMessageContent)
                       .filter(this::hasNonEmptyData)
                       .map(this::getMessageResult)
                       .orElseGet(MessageResult::empty);
    }

    private MessageResult getMessageResult(List<MessageContent> content) {
        var result = MessageResult.builder();
        for (var c : content) {
            result = this.getMessageResultData(
                    c,
                    result
            );
        }

        return result.build();
    }

    private MessageResult.Builder getMessageResultData(
            MessageContent content,
            MessageResult.Builder result
    ) {
        if (content instanceof TextMessageContent text) {
            result = result.withMessage(text.getText()
                                            .value());
        } else if (content instanceof ImageFileContent image) {
            result = result.withImageFileId(image.getFileId());
        }

        return result;
    }

    private List<MessageContent> getMessageContent(List<MessageResponse> data) {
        return data.get(0)
                   .content();
    }

    private <T> boolean hasNonEmptyData(List<T> data) {
        return !data.isEmpty();
    }
}