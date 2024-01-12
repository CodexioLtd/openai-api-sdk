package bg.codexio.ai.openai.api.payload.message.content;

import bg.codexio.ai.openai.api.payload.message.content.annotation.Annotation;

import java.util.List;

public record TextContent(
        String value,
        List<Annotation> annotations
) {}
