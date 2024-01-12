package bg.codexio.ai.openai.api.payload.run.response.error;

public record LastError(
        String code,
        String message
) {}
