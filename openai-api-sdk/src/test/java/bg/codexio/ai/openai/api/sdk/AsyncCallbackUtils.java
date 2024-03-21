package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class AsyncCallbackUtils {

    public static <T> CallbackData<T> prepareCallback(Class<T> type) {
        T[] arr = (T[]) new Object[1];
        Consumer<T> callback = mock(Consumer.class);
        doAnswer(invocation -> {
            arr[0] = invocation.getArgument(0);

            return null;
        }).when(callback)
          .accept(any(type));

        return new CallbackData<>(
                callback,
                arr
        );
    }

    public static void mockAsyncExecution(
            OpenAIHttpExecutor executor,
            Object response,
            String rawResponse
    ) {
        doAnswer(invocation -> {
            var callback = invocation.getArgument(
                    1,
                    Consumer.class
            );
            callback.accept(rawResponse.substring(
                    0,
                    rawResponse.length() / 2
            ));
            callback.accept(rawResponse.substring(rawResponse.length() / 2));

            var finalizer = invocation.getArgument(
                    2,
                    Consumer.class
            );
            finalizer.accept(response);

            return null;
        }).when(executor)
          .executeAsync(
                  any(),
                  any(),
                  any()
          );
    }

    public static void mockAsyncExecutionWithPathVariable(
            OpenAIHttpExecutor executor,
            Object response,
            String rawResponse
    ) {
        doAnswer(invocation -> {
            var callback = invocation.getArgument(
                    2,
                    Consumer.class
            );
            callback.accept(rawResponse.substring(
                    1,
                    rawResponse.length() / 2
            ));
            callback.accept(rawResponse.substring(rawResponse.length() / 2));

            var finalizer = invocation.getArgument(
                    3,
                    Consumer.class
            );
            finalizer.accept(response);

            return null;
        }).when(executor)
          .executeAsyncWithPathVariable(
                  any(),
                  any(),
                  any(),
                  any()
          );
    }

    public static void mockAsyncExecutionWithPathVariables(
            OpenAIHttpExecutor executor,
            Object response,
            String rawResponse
    ) {
        doAnswer(invocation -> {
            var callback = invocation.getArgument(
                    0,
                    Consumer.class
            );
            callback.accept(rawResponse.substring(
                    1,
                    rawResponse.length() / 2
            ));
            callback.accept(rawResponse.substring(rawResponse.length() / 2));

            var finalizer = invocation.getArgument(
                    1,
                    Consumer.class
            );
            finalizer.accept(response);

            return null;
        }).when(executor)
          .retrieveAsync(
                  any(),
                  any(),
                  any()
          );
    }

    public static class CallbackData<T> {
        private final Consumer<T> callback;
        private final T[] arr;

        public CallbackData(
                Consumer<T> callback,
                T[] arr
        ) {
            this.callback = callback;
            this.arr = arr;
        }

        public Consumer<T> callback() {
            return this.callback;
        }

        public T data() {

            return this.arr[0];
        }
    }
}
