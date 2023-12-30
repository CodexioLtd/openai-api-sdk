package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.sdk.images.color.PopularColor;
import bg.codexio.ai.openai.api.sdk.images.tolerance.ColorDeviation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditingMaskStageTest {

    private EditingMaskStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> editingMaskStage;

    @BeforeEach
    public void setUp() {
        this.editingMaskStage = spy(new EditingMaskStage<>(
                EDIT_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<EditImageRequest>builder()
                                   .withImage(new File(TEST_FILE_PATH)),
                PROMPTFUL_EDIT_RUNTIME_SELECTION
        ));
    }

    @Test
    public void testUnmasked_expectFilledBuilderWithFile() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        var expectedMask = (File) null;

        var nextStage = this.editingMaskStage.unmasked();

        assertAll(
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).asImage(),
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).hasAlphaChannel(eq(image)),
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(eq(expectedMask)),
                () -> this.verifyMaskIsNull(nextStage)
        );
    }

    @Test
    public void testMasked_withFile_expectFilledBuilderWithFile() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        var expectedMask = new File(TEST_FILE_PATH);

        var nextStage = this.editingMaskStage.masked(new File(TEST_FILE_PATH));

        assertAll(
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(eq(expectedMask)),
                () -> this.verifyMaskIsApplied(nextStage)
        );
    }

    @Test
    public void testMasked_withAlphaZeroAndDeviationAndAlphaChannel_expectFilledNullFile() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        var expectedMask = (File) null;

        var nextStage = this.editingMaskStage.masked(
                1234,
                ColorDeviation.HALF
        );

        assertAll(
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(eq(expectedMask)),
                () -> this.verifyMaskIsNull(nextStage)
        );
    }

    @Test
    public void testMasked_withAlphaZeroAndDeviationWithoutAlphaChannel_expectFilledNullFile() {
        var image = mock(BufferedImage.class);
        var alphaZeroHex = 1;
        var deviation = 0.5;

        try (var imageIoUtils = mockStatic(ImageIO.class)) {
            doReturn(image).when(this.editingMaskStage)
                           .asImage();
            doReturn(false).when(this.editingMaskStage)
                           .hasAlphaChannel(eq(image));

            var withAlphaImage = mock(BufferedImage.class);
            doReturn(withAlphaImage).when(this.editingMaskStage)
                                    .withAlphaChannel(
                                            eq(image),
                                            eq(alphaZeroHex),
                                            eq(deviation)
                                    );

            this.editingMaskStage.masked(
                    alphaZeroHex,
                    ColorDeviation.HALF
            );

            verify(
                    this.editingMaskStage,
                    times(1)
            ).resetImageWithAlpha(
                    eq(image),
                    eq(alphaZeroHex),
                    eq(deviation)
            );
        }
    }

    @Test
    public void testMaskedWithAlphaZeroAndDeviationWithoutAlphaChannel_shouldThrowAnError() {
        var image = mock(BufferedImage.class);
        var alphaZeroHex = 1;
        var deviation = 0.5;

        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(false).when(this.editingMaskStage)
                       .hasAlphaChannel(eq(image));

        doThrow(new RuntimeException()).when(this.editingMaskStage)
                                       .withAlphaChannel(
                                               eq(image),
                                               eq(alphaZeroHex),
                                               eq(deviation)
                                       );

        assertAll(
                () -> assertThrows(
                        RuntimeException.class,
                        () -> this.editingMaskStage.masked(
                                alphaZeroHex,
                                ColorDeviation.HALF
                        )
                ),
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).resetImageWithAlpha(
                        eq(image),
                        eq(alphaZeroHex),
                        eq(deviation)
                )
        );
    }

    @Test
    public void testMaskedWithDeviation_expectMethodsCalled() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.editingMaskStage.masked(1234);

        assertAll(
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(1234),
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(
                        1234,
                        ColorDeviation.THE_SAME
                )
        );
    }

    @Test
    public void testMasked_withRgb_expectCorrectMethodChain() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.editingMaskStage.masked(
                2,
                3,
                4
        );

        assertAll(
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(
                        2,
                        3,
                        4
                ),
                () -> verify(
                        this.editingMaskStage,
                        times(1)
                ).masked(
                        2,
                        3,
                        4,
                        ColorDeviation.THE_SAME
                )
        );
    }

    @Test
    public void testMasked_withHex_expectCorrectMethodChainAndTransformedValues() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.editingMaskStage.masked("#FFFFFF");

        this.testWithHex(
                "#FFFFFF",
                16777215
        );
    }

    @Test
    public void testMasked_withHexLonger_expectCorrectMethodChainAndTransformedValues() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.editingMaskStage.masked("#FFFFFFFF");

        this.testWithHex(
                "#FFFFFFFF",
                16777215
        );
    }

    @Test
    public void testMasked_withHexAndSmallTransparency_expectCorrectMethodChainAndTransformedValues() {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.editingMaskStage.smallTransparency();

        verify(
                this.editingMaskStage,
                times(1)
        ).masked(
                PopularColor.WHITE,
                ColorDeviation.SMALL_DIFFERENCE
        );
    }


    @Test
    public void testAreColorsSimilar_sameColors_expectTrue() {
        var color1 = new Color(
                100,
                150,
                200
        ).getRGB();
        var color2 = new Color(
                100,
                150,
                200
        ).getRGB();
        var tolerance = 0.1;

        var result = EditingMaskStage.areColorsSimilar(
                color1,
                color2,
                tolerance
        );

        assertTrue(result);
    }

    @Test
    public void testAreColorsSimilar_differentColors_expectFalse() {
        var color1 = new Color(
                100,
                150,
                200
        ).getRGB();
        var color2 = new Color(
                200,
                150,
                100
        ).getRGB();
        var tolerance = 0.1;

        var result = EditingMaskStage.areColorsSimilar(
                color1,
                color2,
                tolerance
        );

        assertFalse(result);
    }

    @Test
    public void testAreColorsSimilar_similarColorsWithHigherTolerance_expectTrue() {
        var color1 = new Color(
                100,
                150,
                200
        ).getRGB();
        var color2 = new Color(
                120,
                140,
                180
        ).getRGB();
        var tolerance = 0.3; // Adjust tolerance as needed

        var result = EditingMaskStage.areColorsSimilar(
                color1,
                color2,
                tolerance
        );

        assertTrue(result);
    }


    @Test
    public void testWithAlphaChannel_expectCorrectColorIntersections() {
        var alphaZeroHex = 0x00;
        var deviation = 0.1;

        var mockImage = new BufferedImage(
                2,
                2,
                BufferedImage.TYPE_INT_ARGB
        );
        mockImage.setRGB(
                0,
                0,
                0xFF000000
        ); // Black with full alpha
        mockImage.setRGB(
                1,
                0,
                0xFFFF0000
        ); // Red with full alpha
        mockImage.setRGB(
                0,
                1,
                0xFF00FF00
        ); // Green with full alpha
        mockImage.setRGB(
                1,
                1,
                0xFF0000FF
        ); // Blue with full alpha

        var expectedResult = new BufferedImage(
                2,
                2,
                BufferedImage.TYPE_INT_ARGB
        );
        expectedResult.setRGB(
                0,
                0,
                0x00000000
        ); // Black with zero alpha
        expectedResult.setRGB(
                1,
                0,
                0xFFFF0000
        ); // Red with full alpha
        expectedResult.setRGB(
                0,
                1,
                0xFF00FF00
        ); // Green with full alpha
        expectedResult.setRGB(
                1,
                1,
                0xFF0000FF
        ); // Blue with full alpha

        var result = this.editingMaskStage.withAlphaChannel(
                mockImage,
                alphaZeroHex,
                deviation
        );

        assertEquals(
                expectedResult.getWidth(),
                result.getWidth()
        );
        assertEquals(
                expectedResult.getHeight(),
                result.getHeight()
        );

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                assertEquals(
                        expectedResult.getRGB(
                                x,
                                y
                        ),
                        result.getRGB(
                                x,
                                y
                        )
                );
            }
        }
    }

    @Test
    public void testHasAlphaChannel_withAlphaChannel_expectTrue() {
        var mockImage = mock(BufferedImage.class);
        when(mockImage.getColorModel()).thenReturn(mock(ColorModel.class));
        when(mockImage.getColorModel()
                      .hasAlpha()).thenReturn(true);

        var result = this.editingMaskStage.hasAlphaChannel(mockImage);

        assertTrue(result);
    }

    @Test
    public void testHasAlphaChannel_withoutAlphaChannel_expectFalse() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getColorModel()).thenReturn(mock(ColorModel.class));
        when(mockImage.getColorModel()
                      .hasAlpha()).thenReturn(false);

        boolean result = this.editingMaskStage.hasAlphaChannel(mockImage);

        assertFalse(result);
    }

    @Test
    public void testAsImage_successfulImageRead_expectBufferedImage() {
        var expectedBufferedImage = new BufferedImage(
                1,
                1,
                BufferedImage.TYPE_INT_ARGB
        );
        var mockImageFile = mock(File.class);

        this.editingMaskStage.builder.withImage(mockImageFile);

        try (var imageIoUtils = mockStatic(ImageIO.class)) {
            imageIoUtils.when(() -> ImageIO.read(any(File.class)))
                        .thenReturn(expectedBufferedImage);

            var bufferedImage = this.editingMaskStage.asImage();

            assertEquals(
                    expectedBufferedImage,
                    bufferedImage
            );
        }
    }

    @Test
    public void testAsImage_unsuccessfulImageRead_expectException() {
        try (var imageIoUtils = mockStatic(ImageIO.class)) {
            imageIoUtils.when(() -> ImageIO.read(any(File.class)))
                        .thenThrow(new RuntimeException("ImageIO.read failed"));

            assertThrows(
                    RuntimeException.class,
                    editingMaskStage::asImage
            );
        }
    }

    private void testWithHex(
            String hex,
            Integer expected
    ) {
        var image = mock(BufferedImage.class);
        doReturn(image).when(this.editingMaskStage)
                       .asImage();
        doReturn(true).when(this.editingMaskStage)
                      .hasAlphaChannel(eq(image));

        this.testWithHex(
                hex,
                expected,
                ColorDeviation.THE_SAME
        );
    }

    private void testWithHex(
            String hex,
            Integer expected,
            ColorDeviation colorDeviation
    ) {

        verify(
                this.editingMaskStage,
                times(1)
        ).masked(
                hex,
                colorDeviation
        );
        verify(
                this.editingMaskStage,
                times(1)
        ).masked(
                expected,
                colorDeviation
        );
    }

    private void verifyMaskIsApplied(ImageConfigurationStage<EditImageRequest> nextStage) {
        assertEquals(
                TEST_FILE_PATH.replace(
                        "/",
                        File.separator
                ),
                nextStage.builder.mask()
                                 .getPath()
        );
    }

    private void verifyMaskIsNull(ImageConfigurationStage<EditImageRequest> nextStage) {
        assertNull(nextStage.builder.mask());
    }

}
