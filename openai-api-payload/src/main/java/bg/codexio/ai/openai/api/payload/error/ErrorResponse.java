package bg.codexio.ai.openai.api.payload.error;

public record ErrorResponse(
        String message,
        String type,
        String param,
        String code
) {}
