package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.images.Dimensions;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.Quality;
import bg.codexio.ai.openai.api.payload.images.Style;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SimplifiedStageTest<R extends ImageRequest> {

    @Test
    public void testGenerate_withManualPrompt_expectSuccessfulResponseUrl() {
        var sdkAuth = mock(SdkAuth.class);
        when(sdkAuth.credentials()).thenReturn(new ApiCredentials(""));

        var simplifiedStage = spy(new SimplifiedStage<>(sdkAuth));

        ImagesTerminalStage<CreateImageRequest,
                PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> terminalStageMock = mock(ImagesTerminalStage.class);

        when(terminalStageMock.andRespond()).thenReturn(mock(PromptfulImagesRuntimeSelectionStage.class));
        when(terminalStageMock.andRespond()
                              .immediate()).thenReturn(mock(SynchronousApi.class));
        when(terminalStageMock.andRespond()
                              .immediate()
                              .generate(TEST_PROMPT)).thenReturn(mock(SynchronousExecutor.class));
        when(terminalStageMock.andRespond()
                              .immediate()
                              .generate(TEST_PROMPT)
                              .andGetRaw()).thenReturn(IMAGE_DATA_RESPONSE);

        doReturn(terminalStageMock).when(simplifiedStage)
                                   .ensureCreatorTerminalStage();

        var response = simplifiedStage.generate(TEST_PROMPT);

        assertEquals(
                "test-url",
                response
        );
    }


    @Test
    public void testEnsureCreatorTerminalStage_expectFilledBuilder() {
        var sdkAuthUtil = mock(SdkAuth.class);
        when(sdkAuthUtil.credentials()).thenReturn(new ApiCredentials(""));

        var simplifiedStage = new SimplifiedStage<>(sdkAuthUtil);

        var ensuredTerminalStage = simplifiedStage.ensureCreatorTerminalStage();

        assertAll(
                () -> modelIsDalle3(ensuredTerminalStage),
                () -> assertEquals(
                        Style.HYPER_REAL.val(),
                        ensuredTerminalStage.builder.style()
                ),
                () -> assertEquals(
                        Quality.HIGH_QUALITY.val(),
                        ensuredTerminalStage.builder.quality()
                ),
                () -> assertEquals(
                        Dimensions.LANDSCAPE_1792.val(),
                        ensuredTerminalStage.builder.size()
                ),
                () -> assertEquals(
                        Format.URL.val(),
                        ensuredTerminalStage.builder.responseFormat()
                )
        );
    }
}
