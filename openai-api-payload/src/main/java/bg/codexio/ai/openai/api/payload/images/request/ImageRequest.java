package bg.codexio.ai.openai.api.payload.images.request;

import bg.codexio.ai.openai.api.payload.Streamable;

public interface ImageRequest
        extends Streamable {
    String prompt();

    String model();

    Integer n();

    String responseFormat();

    String size();

    String user();
}
