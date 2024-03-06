package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.payload.message.content.ImageFileContent;
import bg.codexio.ai.openai.api.payload.message.content.MessageContent;
import bg.codexio.ai.openai.api.payload.message.content.TextMessageContent;
import bg.codexio.ai.openai.api.payload.message.content.annotation.Annotation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FileCitationAnnotation;
import bg.codexio.ai.openai.api.payload.message.content.annotation.FilePathAnnotation;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.file.FileResult;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MessageAnswersRetrievalTypeStage {

    private final ListMessagesResponse listMessagesResponse;

    public MessageAnswersRetrievalTypeStage(ListMessagesResponse listMessagesResponse) {
        this.listMessagesResponse = listMessagesResponse;
    }

    public MessageResult retrieve() {
        return this.getResult(this.listMessagesResponse
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
            result = this.getFileData(
                    text.getText()
                        .annotations(),
                    result
            );
        } else if (content instanceof ImageFileContent image) {
            result = result.withImageFileId(image.getFileId());
        }

        return result;
    }

    private MessageResult.Builder getFileData(
            List<Annotation> annotations,
            MessageResult.Builder result
    ) {
        if (!annotations.isEmpty()) {
            for (var a : annotations) {
                if (a instanceof FilePathAnnotation filePath) {
                    var fileId = filePath.getFilePath()
                                         .fileId();
                    var fileText = filePath.text()
                                           .split("/");
                    var fileName = fileText[fileText.length - 1];
                    result = result.withFileResult(FileResult.builder()
                                                             .withId(fileId)
                                                             .withFileName(fileName));
                } else if (a instanceof FileCitationAnnotation fileCitation) {
                    var message = result.message();
                    if (Objects.nonNull(message)) {
                        var messageBuilder =
                                new StringBuilder(result.message());
                        messageBuilder.replace(
                                fileCitation.startIndex(),
                                fileCitation.endIndex(),
                                fileCitation.getFileCitation()
                                            .quote()
                        );
                        result = result.withMessage(messageBuilder.toString());
                    }
                }
            }
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