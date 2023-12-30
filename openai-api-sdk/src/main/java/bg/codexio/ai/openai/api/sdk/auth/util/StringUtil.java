package bg.codexio.ai.openai.api.sdk.auth.util;

public final class StringUtil {

    private StringUtil() {

    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim()
                                 .isEmpty();
    }
}
