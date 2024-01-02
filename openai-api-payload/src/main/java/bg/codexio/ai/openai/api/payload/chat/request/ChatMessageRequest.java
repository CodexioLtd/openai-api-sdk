package bg.codexio.ai.openai.api.payload.chat.request;

import bg.codexio.ai.openai.api.payload.Streamable;
import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a
 * <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion#Request body</a>
 */
public final class ChatMessageRequest
        implements Streamable {
    private final String model;
    private final List<ChatMessage> messages;
    private final Double frequencyPenalty;
    private final Map<String, Integer> logitBias;
    private final Integer maxTokens;
    private final Integer n;
    private final Double presencePenalty;
    private final Integer seed;
    private final String[] stop;
    private final boolean stream;
    private final Double temperature;
    private final Double topP;
    private final List<ChatTool> tools;
    private final ChatTool.ChatToolChoice toolChoice;
    private final String user;

    public ChatMessageRequest() {
        this(null,
             new ArrayList<>(),
             0.0,
             null,
             150,
             1,
             0.0,
             null,
             null,
             false,
             1.0,
             1.0,
             new ArrayList<>(),
             null,
             null);
    }

    public ChatMessageRequest(
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
        this.model = model;
        this.messages = messages;
        this.frequencyPenalty = frequencyPenalty;
        this.logitBias = logitBias;
        this.maxTokens = maxTokens;
        this.n = n;
        this.presencePenalty = presencePenalty;
        this.seed = seed;
        this.stop = stop;
        this.stream = stream;
        this.temperature = temperature;
        this.topP = topP;
        this.tools = tools;
        this.toolChoice = toolChoice;
        this.user = user;
    }

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

    @JsonProperty
    public String model() {
        return model;
    }

    @JsonProperty
    public List<ChatMessage> messages() {
        return messages;
    }

    @JsonProperty
    public Double frequencyPenalty() {
        return frequencyPenalty;
    }

    @JsonProperty
    public Map<String, Integer> logitBias() {
        return logitBias;
    }

    @JsonProperty
    public Integer maxTokens() {
        return maxTokens;
    }

    @JsonProperty
    public Integer n() {
        return n;
    }

    @JsonProperty
    public Double presencePenalty() {
        return presencePenalty;
    }

    @JsonProperty
    public Integer seed() {
        return seed;
    }

    @JsonProperty
    public String[] stop() {
        return stop;
    }

    @JsonProperty
    public Double temperature() {
        return temperature;
    }

    @JsonProperty
    public Double topP() {
        return topP;
    }

    @JsonProperty
    public List<ChatTool> tools() {
        return tools;
    }

    @JsonProperty
    public ChatTool.ChatToolChoice toolChoice() {
        return toolChoice;
    }

    @JsonProperty
    public String user() {
        return user;
    }

    @Override
    @JsonProperty
    public boolean stream() {
        return stream;
    }

    public static final class Builder {
        private final String model;
        private final List<ChatMessage> messages;
        private final Double frequencyPenalty;
        private final Map<String, Integer> logitBias;
        private final Integer maxTokens;
        private final Integer n;
        private final Double presencePenalty;
        private final Integer seed;
        private final String[] stop;
        private final boolean stream;
        private final Double temperature;
        private final Double topP;
        private final List<ChatTool> tools;
        private final ChatTool.ChatToolChoice toolChoice;
        private final String user;

        public Builder(
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
            this.model = model;
            this.messages = messages;
            this.frequencyPenalty = frequencyPenalty;
            this.logitBias = logitBias;
            this.maxTokens = maxTokens;
            this.n = n;
            this.presencePenalty = presencePenalty;
            this.seed = seed;
            this.stop = stop;
            this.stream = stream;
            this.temperature = temperature;
            this.topP = topP;
            this.tools = tools;
            this.toolChoice = toolChoice;
            this.user = user;
        }

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

        public Builder withTemperature(Double temperature) {
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

        public String model() {
            return model;
        }

        public List<ChatMessage> messages() {
            return messages;
        }

        public Double frequencyPenalty() {
            return frequencyPenalty;
        }

        public Map<String, Integer> logitBias() {
            return logitBias;
        }

        public Integer maxTokens() {
            return maxTokens;
        }

        public Integer n() {
            return n;
        }

        public Double presencePenalty() {
            return presencePenalty;
        }

        public Integer seed() {
            return seed;
        }

        public String[] stop() {
            return stop;
        }

        public boolean stream() {
            return stream;
        }

        public Double temperature() {
            return temperature;
        }

        public Double topP() {
            return topP;
        }

        public List<ChatTool> tools() {
            return tools;
        }

        public ChatTool.ChatToolChoice toolChoice() {
            return toolChoice;
        }

        public String user() {
            return user;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Builder) obj;
            return Objects.equals(
                    this.model,
                    that.model
            ) && Objects.equals(
                    this.messages,
                    that.messages
            ) && Objects.equals(
                    this.frequencyPenalty,
                    that.frequencyPenalty
            ) && Objects.equals(
                    this.logitBias,
                    that.logitBias
            ) && Objects.equals(
                    this.maxTokens,
                    that.maxTokens
            ) && Objects.equals(
                    this.n,
                    that.n
            ) && Objects.equals(
                    this.presencePenalty,
                    that.presencePenalty
            ) && Objects.equals(
                    this.seed,
                    that.seed
            ) && Objects.equals(
                    this.stop,
                    that.stop
            ) && this.stream == that.stream && Objects.equals(
                    this.temperature,
                    that.temperature
            ) && Objects.equals(
                    this.topP,
                    that.topP
            ) && Objects.equals(
                    this.tools,
                    that.tools
            ) && Objects.equals(
                    this.toolChoice,
                    that.toolChoice
            ) && Objects.equals(
                    this.user,
                    that.user
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(
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

        @Override
        public String toString() {
            return "Builder[" + "model=" + model + ", " + "messages=" + messages
                    + ", " + "frequencyPenalty=" + frequencyPenalty + ", "
                    + "logitBias=" + logitBias + ", " + "maxTokens=" + maxTokens
                    + ", " + "n=" + n + ", " + "presencePenalty="
                    + presencePenalty + ", " + "seed=" + seed + ", " + "stop="
                    + stop + ", " + "stream=" + stream + ", " + "temperature="
                    + temperature + ", " + "topP=" + topP + ", " + "tools="
                    + tools + ", " + "toolChoice=" + toolChoice + ", " + "user="
                    + user + ']';
        }

    }
}
