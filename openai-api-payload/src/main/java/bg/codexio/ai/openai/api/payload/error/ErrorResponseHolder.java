package bg.codexio.ai.openai.api.payload.error;

import java.util.Objects;

public final class ErrorResponseHolder {
    private final ErrorResponse error;

    public ErrorResponseHolder(
            ErrorResponse error
    ) {
        this.error = error;
    }

    public ErrorResponse error() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ErrorResponseHolder) obj;
        return Objects.equals(this.error,
                              that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return "ErrorResponseHolder[" + "error=" + error + ']';
    }
}
