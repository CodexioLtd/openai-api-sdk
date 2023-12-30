package bg.codexio.ai.openai.api.http;

import java.util.concurrent.TimeUnit;

/**
 * Representation of an HTTP Timeout.
 * Can be used for all call, connect and read.
 */
public record HttpTimeout(
        long period,
        TimeUnit timeUnit
) {}
