package bg.codexio.ai.openai.api.http;

/**
 * Context object hinting the variety of timeouts
 */
public record HttpTimeouts(
        HttpTimeout call,
        HttpTimeout connect,
        HttpTimeout read
) {}
