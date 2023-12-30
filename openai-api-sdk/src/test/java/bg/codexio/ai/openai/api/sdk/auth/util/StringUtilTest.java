package bg.codexio.ai.openai.api.sdk.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest {

    @Test
    public void testIsNullOrBlank_withNull_shouldReturnTrue() {
        assertTrue(StringUtil.isNullOrBlank(null));
    }

    @Test
    public void testIsNullOrBlank_withEmptyString_shouldReturnTrue() {
        assertTrue(StringUtil.isNullOrBlank(""));
    }

    @Test
    public void testIsNullOrBlank_withBlankString_shouldReturnTrue() {
        assertTrue(StringUtil.isNullOrBlank("   "));
    }

    @Test
    public void testIsNullOrBlank_withNonBlankString_shouldReturnFalse() {
        assertFalse(StringUtil.isNullOrBlank("Codexio OpenAI sdk is great"));
    }
}
