package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;
import bg.codexio.ai.openai.api.sdk.auth.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * When instantiated and {@link #credentials()} is called,
 * already configured json file is scanned for credentials.
 * The default expected file name is <b>openai-credentials.json</b><br>
 * and read as a resource (so it should reside in resources folder).
 * The file name and the path type can be modified by calling some of
 * the factory functions below.
 */
public class FromJson
        implements SdkAuth {

    public static final FromJson AUTH = new FromJson(
            "openai-credentials.json",
            false
    );

    private final String jsonFile;
    private final boolean isFullPath;

    /**
     * @param jsonFile   string value with the file location
     * @param isFullPath boolean explaining if the path is <b>absolute</b> or
     *                   <b>relative</b>
     */
    FromJson(
            String jsonFile,
            boolean isFullPath
    ) {
        this.jsonFile = jsonFile;
        this.isFullPath = isFullPath;
    }

    /**
     * Factory method denoting a JSON resource file with <b>relative path</b>
     *
     * @param resourceFileName the relative path of the JSON resource
     * @return new instance of the current class with configured credentials
     * field
     */
    public static FromJson resource(String resourceFileName) {
        return new FromJson(
                resourceFileName,
                false
        );
    }

    /**
     * Factory method denoting a JSON file with <b>absolute path</b>
     *
     * @param fullPathFileName the absolute path of the JSON file
     * @return new instance of the current class with configured credentials
     * field
     */
    public static FromJson file(String fullPathFileName) {
        return new FromJson(
                fullPathFileName,
                true
        );
    }

    private static String extractValue(
            String json,
            String key
    ) {
        var pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        var matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    /**
     * In this implementation we first look for the <b>apiKey</b> in the
     * provided <b>JSON file</b><br>
     * If this variable is missing, a {@link NotValidAuthenticationMethod}
     * error is thrown, as the authentication can never proceed without an
     * <b>api key</b>.<br>
     * If <b>orgId</b> or <b>organizationId</b> is configured, it is applied,
     * otherwise <b>the default empty value is used</b><br>
     * Check {@link AvailableKeys} for the correct naming.
     *
     * @return ready for usage instance of {@link ApiCredentials}
     */
    @Override
    public ApiCredentials credentials() {
        var json = this.jsonContent();
        var apiKey = extractValue(
                json,
                AvailableKeys.apiKey.name()
        );
        if (StringUtil.isNullOrBlank(apiKey)) {
            throw new NotValidAuthenticationMethod(
                    "The given JSON file: " + this.jsonFile
                            + " does not have a definition of the key \""
                            + AvailableKeys.apiKey.name() + "\".");
        }

        var orgId = extractValue(
                json,
                AvailableKeys.orgId.name()
        );
        if (StringUtil.isNullOrBlank(orgId)) {
            orgId = extractValue(
                    json,
                    AvailableKeys.organizationId.name()
            );
        }

        var baseUrl = extractValue(
                json,
                AvailableKeys.baseUrl.name()
        );

        return new ApiCredentials(
                apiKey,
                orgId,
                baseUrl
        );
    }

    private String jsonContent() {
        try (var stream = this.asInputStream()) {
            return new String(
                    stream.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (FileNotFoundException e) {
            throw new NotValidAuthenticationMethod(
                    "Absolute file " + this.jsonFile + " not found.");
        } catch (IOException e) {
            throw new NotValidAuthenticationMethod(
                    "Error reading the content of the " + (
                            this.isFullPath
                            ? "absolute "
                            : ""
                    ) + "file " + this.jsonFile);
        } catch (NullPointerException e) {
            throw new NotValidAuthenticationMethod(
                    "Non-existing resource file: " + this.jsonFile);
        }
    }

    protected InputStream asInputStream() throws IOException {
        if (this.isFullPath) {
            return new FileInputStream(this.jsonFile);
        }

        return this.getClass()
                   .getClassLoader()
                   .getResourceAsStream(this.jsonFile);
    }

    public enum AvailableKeys {
        apiKey,
        orgId,
        organizationId,
        baseUrl
    }
}
