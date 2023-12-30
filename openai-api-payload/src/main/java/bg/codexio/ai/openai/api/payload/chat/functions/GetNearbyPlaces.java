package bg.codexio.ai.openai.api.payload.chat.functions;

import bg.codexio.ai.openai.api.payload.chat.ChatFunction;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionChoice;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionTool;

import java.util.Map;

public class GetNearbyPlaces
        extends ChatFunction {

    public static final FunctionTool FUNCTION =
            new FunctionTool(new GetNearbyPlaces());
    public static final FunctionTool.FunctionToolChoice CHOICE =
            new FunctionTool.FunctionToolChoice(new GetNearbyPlacesFunctionChoice());

    private GetNearbyPlaces() {
        super(
                "Gets the nearby places of a given type, in a certain radius "
                        + "for a geolocation.",
                "getNearbyPlaces",
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
                                        "Comma separated geo coordinates such"
                                                + " as"
                                                + " 45,6789,12.3456"
                                ),
                                "radius",
                                Map.of(
                                        "type",
                                        "number",
                                        "description",
                                        "Radius in meters such as 1500 for 1"
                                                + ".5km"
                                ),
                                "type",
                                Map.of(
                                        "type",
                                        "string",
                                        "description",
                                        "Place type such as gas station, "
                                                + "restaurant, hotel, atm, etc."
                                )
                        )
                ),
                GetNearbyPlaces.CHOICE
        );
    }

    public record GetNearbyPlacesFunctionChoice()
            implements FunctionChoice {
        @Override
        public String name() {
            return GetNearbyPlaces.FUNCTION.function()
                                           .getName();
        }
    }

}
