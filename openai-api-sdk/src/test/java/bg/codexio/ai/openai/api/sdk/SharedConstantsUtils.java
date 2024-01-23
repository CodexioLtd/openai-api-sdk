package bg.codexio.ai.openai.api.sdk;

import java.io.File;
import java.util.Map;

public class SharedConstantsUtils {

    public static final String API_CREDENTIALS = "test-key";
    public static final String DEFAULT_ROLE = "user";
    public static final File FILE = new File(
            "src/test/resources/fake-file" + ".txt");
    public static final String[] FILE_IDS_VAR_ARGS = new String[]{
            "test_file_var_arg_id_1", "test_file_var_arg_id_2"
    };
    public static final String[] METADATA_VAR_ARGS = new String[]{
            "metaKey", "metaValue"
    };
    public static final Map<String, String> METADATA_MAP = Map.of(
            METADATA_VAR_ARGS[0],
            METADATA_VAR_ARGS[1]
    );

    private SharedConstantsUtils() {
    }
}