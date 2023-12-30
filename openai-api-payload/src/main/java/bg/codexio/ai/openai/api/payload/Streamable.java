package bg.codexio.ai.openai.api.payload;

/**
 * Represents a request objects
 * that may belong to a streamable API.
 * This does not necessary means,
 * that Streaming is supported by this object,
 * hence the {@link #stream()} method.
 */
public interface Streamable {

    boolean stream();
}
