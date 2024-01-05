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
                                                + "Francisco," + " CA"
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

    public static final class GetCurrentWeatherFunctionChoice
            implements FunctionChoice {
        public GetCurrentWeatherFunctionChoice() {
        }

        @Override
        public String name() {
            return GetCurrentWeather.FUNCTION.function()
                                             .getName();
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this
                    || obj != null && obj.getClass() == this.getClass();
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return "GetCurrentWeatherFunctionChoice[]";
        }

    }

}
