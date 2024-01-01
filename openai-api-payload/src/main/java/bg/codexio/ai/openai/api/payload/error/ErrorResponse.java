package bg.codexio.ai.openai.api.payload.error;

import java.util.Objects;

public final class ErrorResponse {
    private final String message;
    private final String type;
    private final String param;
    private final String code;

    public ErrorResponse(
            String message,
            String type,
            String param,
            String code
    ) {
        this.message = message;
        this.type = type;
        this.param = param;
        this.code = code;
    }

    public String message() {
        return message;
    }

    public String type() {
        return type;
    }

    public String param() {
        return param;
    }

    public String code() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ErrorResponse) obj;
        return Objects.equals(this.message,
                              that.message) && Objects.equals(this.type,
                                                              that.type)
                && Objects.equals(this.param,
                                  that.param) && Objects.equals(this.code,
                                                                that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message,
                            type,
                            param,
                            code);
    }

    @Override
    public String toString() {
        return "ErrorResponse[" + "message=" + message + ", " + "type=" + type
                + ", " + "param=" + param + ", " + "code=" + code + ']';
    }
}
