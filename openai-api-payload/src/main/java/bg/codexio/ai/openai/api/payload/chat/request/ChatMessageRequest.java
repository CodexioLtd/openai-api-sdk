package bg.codexio.ai.openai.api.payload.chat.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion#Request body</a>
 */
public record ChatMessageRequest(
        String model,
        List<ChatMessage> messages,
        Double frequencyPenalty,
        Map<String, Integer> logitBias,
        Integer maxTokens,
        Integer n,
        Double presencePenalty,
        Integer seed,
        String[] stop,
        boolean stream,
        Double temperature,
        Double topP,
        List<ChatTool> tools,
        ChatTool.ChatToolChoice toolChoice,
        String user
)
        implements Streamable {

    public static Builder builder() {
        return new Builder(
                "",
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null
        );
    }

    public record Builder(
            String model,
            List<ChatMessage> messages,
            Double frequencyPenalty,
            Map<String, Integer> logitBias,
            Integer maxTokens,
            Integer n,
            Double presencePenalty,
            Integer seed,
            String[] stop,
            boolean stream,
            Double temperature,
            Double topP,
            List<ChatTool> tools,
            ChatTool.ChatToolChoice toolChoice,
            String user
    ) {

        public Builder withModel(String model) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withMessages(List<ChatMessage> messages) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder addMessage(ChatMessage message) {
            var messages = new ArrayList<>(Objects.requireNonNullElse(
                    this.messages,
                    new ArrayList<>()
            ));
            messages.add(message);

            return this.withMessages(messages);
        }

        public Builder withFrequencyPenalty(Double frequencyPenalty) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withLogitBias(Map<String, Integer> logitBias) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withMaxTokens(Integer maxTokens) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withN(Integer n) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withPresencePenalty(Double presencePenalty) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withSeed(Integer seed) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withStop(String[] stop) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder shouldStream(boolean stream) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withTermperature(Double temperature) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withTopP(Double topP) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withTools(List<ChatTool> tools) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder addTool(ChatTool chatTool) {
            var tools = new ArrayList<>(Objects.requireNonNullElse(
                    this.tools,
                    new ArrayList<>()
            ));
            tools.add(chatTool);

            return this.withTools(tools);
        }


        public Builder withToolChoice(ChatTool.ChatToolChoice toolChoice) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public Builder withUser(String user) {
            return new Builder(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }

        public ChatMessageRequest build() {
            return new ChatMessageRequest(
                    model,
                    messages,
                    frequencyPenalty,
                    logitBias,
                    maxTokens,
                    n,
                    presencePenalty,
                    seed,
                    stop,
                    stream,
                    temperature,
                    topP,
                    tools,
                    toolChoice,
                    user
            );
        }
    }
}
