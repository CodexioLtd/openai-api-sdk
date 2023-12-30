package bg.codexio.ai.openai.api.payload.chat.functions;

import bg.codexio.ai.openai.api.payload.chat.ChatFunction;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionChoice;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionTool;

import java.util.Map;

public class GetCurrentWeather
        extends ChatFunction {

    public static final FunctionTool FUNCTION =
            new FunctionTool(new GetCurrentWeather());
    public static final FunctionTool.FunctionToolChoice CHOICE =
            new FunctionTool.FunctionToolChoice(new GetCurrentWeatherFunctionChoice());

    private GetCurrentWeather() {
        super(
                "Get the current weather in a given location",
                "get_current_weather",
                Map.of(
                        "type",
                        "object",
                        "properties",
                        Map.of(
                                "location",
                                Map.of(
                                        "type",
                                        "string",
                                        "description",
                                        "The city and state, e.g. San "
                                                + "Francisco,"
                                                + " CA"
                                ),
                                "unit",
                                Map.of(
                                        "type",
                                        "string",
                                        "enum",
                                        new String[]{"celsius", "fahrenheit"}
                                )
                        ),
                        "required",
                        new String[]{"location"}
                ),
                GetCurrentWeather.CHOICE
        );
    }

    public record GetCurrentWeatherFunctionChoice()
            implements FunctionChoice {
        @Override
        public String name() {
            return GetCurrentWeather.FUNCTION.function()
                                             .getName();
        }
    }

}
