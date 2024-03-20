package bg.codexio.ai.openai.api.sdk.download;

import bg.codexio.ai.openai.api.sdk.download.exception.NoSuchExecutorException;

import static bg.codexio.ai.openai.api.sdk.constant.FactoryConstantsUtils.*;

public class DefaultDownloadExecutorFactory
        implements DownloadExecutorFactory {

    @Override
    public DownloadExecutor create(String executor) {
        return switch (executor) {
            case FILE_EXECUTOR_IDENTIFIER -> new FileDownloadExecutor();
            case IMAGE_EXECUTOR_IDENTIFIER -> new ImageDownloadExecutor();
            case SPEECH_EXECUTOR_IDENTIFIER -> new SpeechDownloadExecutor();
            default -> throw new NoSuchExecutorException();
        };
    }
}
