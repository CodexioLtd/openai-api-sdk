package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.functions.GetNearbyPlaces;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.request.ChatTool;

/**
 * Configures tools such as functions.
 */
public class ToolStage
        extends ChatConfigurationStage {

    ToolStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Adds a tool and set it as <b>toolChoice</b> if true.
     *
     * @param tool         {@link ChatTool} such as
     *                     {@link GetNearbyPlaces#CHOICE}
     * @param shouldChoose boolean whether to add it in <b>toolChoice</b>
     * @return self
     */
    public ToolStage withTool(
            ChatTool tool,
            boolean shouldChoose
    ) {
        return new ToolStage(
                this.executor,
                this.requestBuilder.addTool(tool)
                                   .withToolChoice(shouldChoose
                                                   ? tool.asChoice()
                                                   : null)
        );
    }

    /**
     * Adds a tool and set it as <b>toolChoice</b>.
     *
     * @param tool {@link ChatTool} such as {@link GetNearbyPlaces#CHOICE}
     * @return self
     */
    public ToolStage chooseTool(ChatTool tool) {
        return this.withTool(
                tool,
                true
        );
    }

    /**
     * Adds a tool.
     *
     * @param tool {@link ChatTool} such as {@link GetNearbyPlaces#CHOICE}
     * @return self
     */
    public ToolStage justAdd(ChatTool tool) {
        return this.withTool(
                tool,
                false
        );
    }

    /**
     * Go back
     *
     * @return {@link ManualConfigurationStage}
     */
    public ManualConfigurationStage and() {
        return new ManualConfigurationStage(
                this.executor,
                this.requestBuilder
        );
    }
}
