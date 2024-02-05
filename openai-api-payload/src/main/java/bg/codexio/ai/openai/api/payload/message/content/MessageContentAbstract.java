package bg.codexio.ai.openai.api.payload.message.content;

public abstract class MessageContentAbstract
        implements MessageContent {

    private final String type;

    protected MessageContentAbstract(String type) {
        this.type = type;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}