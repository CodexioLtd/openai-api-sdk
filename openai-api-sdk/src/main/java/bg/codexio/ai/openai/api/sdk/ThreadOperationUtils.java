package bg.codexio.ai.openai.api.sdk;

public final class ThreadOperationUtils {

    private ThreadOperationUtils() {
    }

    public static void sleep(Long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
}