package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

/**
 * Too many IO in this class
 * to leave it only as unit test.
 * Below testing extensively uses IO,
 * classify it as an integration test.
 */
public class FromJsonTest {

    private final Path DEFAULT_CREDS_PATH = new File(this.getClass()
                                                         .getResource("/")
                                                         .getFile() + "openai"
                                                             + "-credentials"
                                                             + ".json").toPath();
    private final Path ADDITIONAL_CREDS_PATH = new File(this.getClass()
                                                            .getResource("/")
                                                            .getFile() + "test"
                                                                + "-credentials"
                                                                + ".json").toPath();

    @Test
    public void testFactory_shouldReturnResourceKey() throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-12345\"\n" + "}"
        );

        var auth = FromJson.AUTH;

        assertEquals(
                new ApiCredentials(
                        "sk-12345",
                        "",
                        ""
                ),
                auth.credentials()
        );
    }

    @Test
    public void testResource_noResourceFile_shouldThrowException() {
        var auth = FromJson.resource("test-credentials.json");

        assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
    }


    @Test
    public void testFactory_emptyKey_expectException() throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey + "\": \"\"\n"
                        + "}"
        );

        var auth = FromJson.AUTH;

        assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
    }

    @Test
    public void testFactory_nullApiKey_expectException() throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey + "\": null\n"
                        + "}"
        );

        var auth = FromJson.AUTH;

        assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
    }

    @Test
    public void testFactory_nullKey_expectException() throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey + "\": null\n"
                        + "}"
        );

        var auth = FromJson.AUTH;

        assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
    }

    @Test
    public void testFactory_shouldReturnResourceKeyAndOrg() throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-12345\",\n" + "  \""
                        + FromJson.AvailableKeys.orgId + "\": \"testOrg\"\n"
                        + "}"
        );

        var auth = FromJson.AUTH;

        assertEquals(
                new ApiCredentials(
                        "sk-12345",
                        "testOrg",
                        ""
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_cannotCloseFileStream_expectException()
            throws IOException {
        var auth = spy(FromJson.AUTH);
        doThrow(new IOException()).when(auth)
                                  .asInputStream();

        var exception = assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
        assertEquals(
                "Error reading the content of the file openai-credentials.json",
                exception.getMessage()
        );
    }

    @Test
    public void testFactory_nullOrgId_shouldReturnResourceKeyAndOrgFallback()
            throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-12345\",\n" + "  \""
                        + FromJson.AvailableKeys.orgId + "\": null,\n" + "  \""
                        + FromJson.AvailableKeys.organizationId
                        + "\": \"testOrg\"\n" + "}"
        );

        var auth = FromJson.AUTH;

        assertEquals(
                new ApiCredentials(
                        "sk-12345",
                        "testOrg",
                        ""
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_shouldReturnResourceKeyAndOrgFallback()
            throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-12345\",\n" + "  \""
                        + FromJson.AvailableKeys.organizationId
                        + "\": \"testOrg\"\n" + "}"
        );

        var auth = FromJson.AUTH;

        assertEquals(
                new ApiCredentials(
                        "sk-12345",
                        "testOrg",
                        ""
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_shouldReturnResourceKeyAndOrgAndBaseUrl()
            throws IOException {
        Files.writeString(
                DEFAULT_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-12345\",\n" + "  \""
                        + FromJson.AvailableKeys.orgId + "\": \"testOrg\",\n"
                        + "  \"" + FromJson.AvailableKeys.baseUrl
                        + "\": \"http://base-url\"\n" + "}"
        );

        var auth = FromJson.AUTH;

        assertEquals(
                new ApiCredentials(
                        "sk-12345",
                        "testOrg",
                        "http://base-url"
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_noResourceFile_shouldThrowException() {
        this.tearDown();
        var auth = FromJson.AUTH;

        assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
    }

    @Test
    public void testResource_expectCredentials() throws IOException {
        Files.writeString(
                ADDITIONAL_CREDS_PATH,
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-123456\",\n" + "  \""
                        + FromJson.AvailableKeys.orgId + "\": \"testOrg\",\n"
                        + "  \"" + FromJson.AvailableKeys.baseUrl
                        + "\": \"http://base-url\"\n" + "}"
        );

        var auth = FromJson.resource("test-credentials.json");

        assertEquals(
                new ApiCredentials(
                        "sk-123456",
                        "testOrg",
                        "http://base-url"
                ),
                auth.credentials()
        );
    }


    @Test
    public void testFile_expectCredentials(@TempDir Path tempDir)
            throws IOException {
        Files.writeString(
                tempDir.resolve("test-auth.json"),
                "{\n" + "  \"" + FromJson.AvailableKeys.apiKey
                        + "\": \"sk-1234567\",\n" + "  \""
                        + FromJson.AvailableKeys.orgId + "\": \"testOrg\",\n"
                        + "  \"" + FromJson.AvailableKeys.baseUrl
                        + "\": \"http://base-url\"\n" + "}"
        );

        var auth = FromJson.file(tempDir.resolve("test-auth.json")
                                        .toAbsolutePath()
                                        .toString());

        assertEquals(
                new ApiCredentials(
                        "sk-1234567",
                        "testOrg",
                        "http://base-url"
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFile_cannotCloseFileStream_expectException()
            throws IOException {
        var auth = spy(FromJson.file("file/does/not/matter"));
        doThrow(new IOException()).when(auth)
                                  .asInputStream();

        var exception = assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
        assertEquals(
                "Error reading the content of the absolute file "
                        + "file/does/not/matter",
                exception.getMessage()
        );
    }

    @Test
    public void testFile_noAbsoluteFile_expectException(@TempDir Path tempDir) {
        var auth = FromJson.file(tempDir.resolve("test-auth.json")
                                        .toAbsolutePath()
                                        .toString());

        var exception = assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
        assertEquals(
                "Absolute file " + tempDir.resolve("test-auth.json")
                        + " not found.",
                exception.getMessage()
        );
    }

    @AfterEach
    public void tearDown() {
        if (DEFAULT_CREDS_PATH.toFile()
                              .exists()) {
            DEFAULT_CREDS_PATH.toFile()
                              .delete();
        }

        if (ADDITIONAL_CREDS_PATH.toFile()
                                 .exists()) {
            ADDITIONAL_CREDS_PATH.toFile()
                                 .delete();
        }
    }
}
