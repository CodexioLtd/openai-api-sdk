package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.images.color.PopularColor;
import bg.codexio.ai.openai.api.sdk.images.tolerance.ColorDeviation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Function;

/**
 * <p>A stage to specify the editable areas of the image.</p>
 * <p>You can choose to:</p>
 * <ul>
 *     <li>Choose <b>unmasked</b> option, which will automatically make only
 *     the purely white areas of the image editable</li>
 *     <li>Upload a custom mask for the file with the transparent areas</li>
 *     <li>Specify the are that should be editable by color.
 *     If you select this option, you'll have to choose
 *     {@link ColorDeviation} level and provide a color in one of the
 *     following forms:
 *          <ul>
 *              <li>Decimal</li>
 *              <li>Hexadecimal</li>
 *              <li>RBG</li>
 *              <li>From the predefined colors selection in
 *              {@link PopularColor}</li>
 *          </ul>
 *     </li>
 * </ul>
 */
public class EditingMaskStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    EditingMaskStage(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Function<ImageRequestBuilder<R>, E> runtimeSelector
    ) {
        super(
                executor,
                builder
        );
        this.runtimeSelector = runtimeSelector;
    }

    protected static boolean areColorsSimilar(
            int left,
            int right,
            double tolerance
    ) {
        var c1 = new Color(left);
        var c2 = new Color(right);

        var redDiff = Math.abs(c1.getRed() - c2.getRed());
        var greenDiff = Math.abs(c1.getGreen() - c2.getGreen());
        var blueDiff = Math.abs(c1.getBlue() - c2.getBlue());

        var maxDiff = 255 * tolerance;

        return (redDiff < maxDiff && greenDiff < maxDiff && blueDiff < maxDiff);
    }

    /**
     * Skips the masking stage.
     *
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> unmasked() {
        return this.masked(PopularColor.WHITE);
    }

    /**
     * @param mask file with the desired mask image.
     *             Sets a custom mask from the file provided.
     *
     *             <p>
     *             <b color="orange">Important note:</b> The mask must be a
     *             <b>valid PNG file, less than 4MB, and have the same
     *             dimensions as the original image for editing</b>. The
     *             fully transparent areas of the mask will be the areas of
     *             the original image that will be edited.
     *             </p>
     *             <p>
     *             If any of those conditions is not followed, it will result
     *             in an error response.
     *             </p>
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(File mask) {
        return new AIModelStage<>(
                this.executor,
                this.builder.withMask(mask),
                this.runtimeSelector
        ).poweredByDallE2();
    }

    /**
     * @param alphaZeroHex decimal value of the color that indicates the
     *                     editable areas
     * @param deviation    {@link ColorDeviation} level of deviation - the
     *                     similarity of the colors
     *                     that should be marked as
     *                     editable
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(
            int alphaZeroHex,
            ColorDeviation deviation
    ) {
        var image = this.asImage();

        if (!this.hasAlphaChannel(image)) {
            this.resetImageWithAlpha(
                    image,
                    alphaZeroHex,
                    deviation.val()
            );
        }

        return this.masked((File) null);
    }

    /**
     * @param color     {@link PopularColor} choice of color that indicates
     *                  the editable areas
     * @param deviation {@link ColorDeviation} level of deviation - the
     *                  similarity of the colors that
     *                  should be marked as editable
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(
            PopularColor color,
            ColorDeviation deviation
    ) {
        return this.masked(
                color.val(),
                deviation
        );
    }

    /**
     * @param color {@link PopularColor} choice of color that indicates the
     *              editable ares
     *              <p>
     *              In this case, the color deviation is automatically set to
     *              the lowest value, which means that
     *              only the specific color with no
     *              deviations will be marked for editing.
     *              </p>
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(PopularColor color) {
        return this.masked(
                color,
                ColorDeviation.THE_SAME
        );
    }

    /**
     * @param alphaZeroHex decimal value of the color that indicates the
     *                     editable areas
     *                     <p>
     *                     In this case, the color deviation is automatically
     *                     set to the lowest value, which means that only the
     *                     specific color with no deviations will be marked
     *                     for editing.
     *                     </p>
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(int alphaZeroHex) {
        return this.masked(
                alphaZeroHex,
                ColorDeviation.THE_SAME
        );
    }

    /**
     * @param red       the amount of <span color="red">red</span> in
     *                  <b>RGB</b> color scale for marking the editable area
     * @param green     the amount of <span color="green">green</span> in
     *                  <b>RGB</b> color scale for marking the editable area
     * @param blue      the amount of <span color="blue">blue</span> in
     *                  <b>RGB</b> color scale for marking the editable area
     * @param deviation {@link ColorDeviation} level of deviation - the
     *                  similarity of the colors that
     *                  should be marked as editable
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(
            int red,
            int green,
            int blue,
            ColorDeviation deviation
    ) {
        return this.masked(
                (red << 16) | (green << 8) | blue,
                deviation
        );
    }

    /**
     * @param red   the amount of <span color="red">red</span> in <b>RGB</b>
     *              color scale for marking the editable area
     * @param green the amount of <span color="green">green</span> in
     *              <b>RGB</b> color scale for marking the editable area
     * @param blue  the amount of <span color="blue">blue</span> in
     *              <b>RGB</b> color scale for marking the editable area
     *              <p>
     *              In this case, the color deviation is automatically set to
     *              the lowest value, which means that only the specific
     *              color with no deviations will be marked for editing.
     *              </p>
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(
            int red,
            int green,
            int blue
    ) {
        return this.masked(
                red,
                green,
                blue,
                ColorDeviation.THE_SAME
        );
    }

    /**
     * @param colorHex  hexadecimal value of the color that indicates the
     *                  editable areas
     * @param deviation {@link ColorDeviation} level of deviation - the
     *                  similarity of the colors that
     *                  should be marked as editable
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(
            String colorHex,
            ColorDeviation deviation
    ) {
        var adaptedHex = colorHex.replace(
                "#",
                ""
        );
        if (adaptedHex.length() >= 8) {
            adaptedHex = adaptedHex.substring(
                    2,
                    8
            );
        }

        return this.masked(
                Integer.parseInt(
                        adaptedHex,
                        16
                ),
                deviation
        );
    }

    /**
     * @param colorHex hexadecimal value of the color that indicates the
     *                 editable areas
     *                 <p>
     *                 In this case, the color deviation is automatically set
     *                 to the lowest value, which means that only the
     *                 specific color with no deviations will be marked for
     *                 editing.
     *                 </p>
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> masked(String colorHex) {
        return this.masked(
                colorHex,
                ColorDeviation.THE_SAME
        );
    }

    /**
     * This choice is almost the same as <b>unmasked</b>, but will handle
     * more shades of white expanding the editable areas in comparison to the
     * other option
     *
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> smallTransparency() {
        return this.masked(
                PopularColor.WHITE,
                ColorDeviation.SMALL_DIFFERENCE
        );
    }

    protected BufferedImage asImage() {
        try {
            return ImageIO.read(this.builder.image());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected BufferedImage withAlphaChannel(
            BufferedImage image,
            int alphaZeroHex,
            double deviation
    ) {
        var newImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        for (var y = 0; y < image.getHeight(); y++) {
            for (var x = 0; x < image.getWidth(); x++) {
                var pixel = image.getRGB(
                        x,
                        y
                );
                if (areColorsSimilar(
                        pixel,
                        alphaZeroHex,
                        deviation
                )) {
                    newImage.setRGB(
                            x,
                            y,
                            alphaZeroHex & pixel
                    );
                } else {
                    newImage.setRGB(
                            x,
                            y,
                            pixel
                    );
                }
            }
        }

        return newImage;
    }

    protected boolean hasAlphaChannel(BufferedImage image) {
        return image.getColorModel()
                    .hasAlpha();
    }

    protected void resetImageWithAlpha(
            BufferedImage image,
            int alphaZeroHex,
            double deviation
    ) {
        try {
            var tempImage = File.createTempFile(
                    "rgba-",
                    ".png"
            );
            ImageIO.write(
                    this.withAlphaChannel(
                            image,
                            alphaZeroHex,
                            deviation
                    ),
                    "PNG",
                    tempImage
            );

            this.builder = this.builder.withImage(tempImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
