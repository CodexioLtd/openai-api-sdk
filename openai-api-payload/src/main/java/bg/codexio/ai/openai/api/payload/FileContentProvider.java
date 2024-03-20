package bg.codexio.ai.openai.api.payload;

public interface FileContentProvider {
    byte[] bytes();

    default String url() {
        return "";
    }
}
