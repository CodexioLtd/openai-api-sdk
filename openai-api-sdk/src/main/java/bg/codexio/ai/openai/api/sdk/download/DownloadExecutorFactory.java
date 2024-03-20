package bg.codexio.ai.openai.api.sdk.download;

public interface DownloadExecutorFactory {
    DownloadExecutor create(String executor);
}
