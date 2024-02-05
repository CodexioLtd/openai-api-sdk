package bg.codexio.ai.openai.api.payload.message.content.annotation;

public abstract class AnnotationAbstract
        implements Annotation {

    private final String type;
    private final String text;
    private final Integer startIndex;
    private final Integer endIndex;

    protected AnnotationAbstract(
            String type,
            String text,
            Integer startIndex,
            Integer endIndex
    ) {
        this.type = type;
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public String text() {
        return this.text;
    }

    @Override
    public Integer startIndex() {
        return this.startIndex;
    }

    @Override
    public Integer endIndex() {
        return this.endIndex;
    }
}