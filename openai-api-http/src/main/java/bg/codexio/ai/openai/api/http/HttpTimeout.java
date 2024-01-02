package bg.codexio.ai.openai.api.http;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Representation of an HTTP Timeout.
 * Can be used for all call, connect and read.
 */
public final class HttpTimeout {
    private final long period;
    private final TimeUnit timeUnit;

    public HttpTimeout(
            long period,
            TimeUnit timeUnit
    ) {
        this.period = period;
        this.timeUnit = timeUnit;
    }

    public long period() {
        return period;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (HttpTimeout) obj;
        return this.period == that.period && Objects.equals(
                this.timeUnit,
                that.timeUnit
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                period,
                timeUnit
        );
    }

    @Override
    public String toString() {
        return "HttpTimeout[" + "period=" + period + ", " + "timeUnit="
                + timeUnit + ']';
    }
}
