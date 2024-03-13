package bg.codexio.ai.openai.api.sdk.file.download;

import bg.codexio.ai.openai.api.payload.file.download.FileDownloadingMeta;
import bg.codexio.ai.openai.api.payload.file.response.FileContentResponse;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE_TEST_ID;

public class InternalAssertions {
    static final String FILE_TEST_NAME = "test_name";

    static final String FILE_TEST_PATH = "var/files/result";

    static final FileDownloadingMeta.Builder FILE_DOWNLOADING_META_WITH_FULL_DATA = FileDownloadingMeta.builder()
                                                                                                       .withFileName(FILE_TEST_NAME)
                                                                                                       .withFileId(FILE_TEST_ID)
                                                                                                       .withTargetFolder(FILE);

    static final FileContentResponse FILE_CONTENT_RESPONSE =
            new FileContentResponse(new byte[]{
            1, 2, 3
    });
}
