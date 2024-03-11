package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static org.mockito.Mockito.mock;

public class InternalAssertions {

    static final CreateThreadHttpExecutor CREATE_THREAD_HTTP_EXECUTOR =
            mock(CreateThreadHttpExecutor.class);

    public static final String THREAD_ID = "thread_test_id";
    static final ModifyThreadHttpExecutor MODIFY_THREAD_HTTP_EXECUTOR =
            mock(ModifyThreadHttpExecutor.class);
    static final String[] THREAD_MESSAGE_CONTENT_ARGS = new String[]{
            "test_message_content_1", "test_message_content_2"
    };
    static final String THREAD_MESSAGE_CONTENT = "test_message_content_3";
    public static final ThreadResponse THREAD_RESPONSE = new ThreadResponse(
            THREAD_ID,
            "test_object",
            0,
            METADATA_MAP
    );

    //    static final ThreadRequestBuilder<ThreadCreationRequest>
    //    THREAD_CREATION_REQUEST_BUILDER = ThreadRequestBuilder
    //    .<ThreadCreationRequest>builder()
    //                                                                                                                   .withSpecificRequestCreator(threadRequestBuilder -> new ThreadCreationRequest(
    //                                                                                                                           threadRequestBuilder.messages(),
    //                                                                                                                           threadRequestBuilder.metadata()
    //                                                                                                                   ));
    //
    //    static final ThreadRequestBuilder<ThreadModificationRequest>
    //    THREAD_MODIFICATION_REQUEST_THREAD_REQUEST_BUILDER =
    //    ThreadRequestBuilder.<ThreadModificationRequest>builder()
    //                                                                                                                                          .withSpecificRequestCreator(threadRequestBuilder -> new ThreadModificationRequest(threadRequestBuilder.metadata()));
    //
    //
    //    static void createThreadRequestIsPopulated
    //    (ThreadConfigurationStage<ThreadCreationRequest> nextStage) {
    //        var request = nextStage.requestBuilder.specificRequestCreator()
    //                                              .apply(nextStage
    //                                              .requestBuilder);
    //
    //        assertAll(
    //                () -> assertEquals(
    //                        request.messages(),
    //                        nextStage.requestBuilder.messages()
    //                ),
    //                () -> assertEquals(
    //                        request.metadata(),
    //                        nextStage.requestBuilder.metadata()
    //                )
    //        );
    //    }
    //
    //    static void modifyThreadRequestIsPopulated
    //    (ThreadConfigurationStage<ThreadModificationRequest> nextStage) {
    //        var request = nextStage.requestBuilder.specificRequestCreator()
    //                                              .apply(nextStage
    //                                              .requestBuilder);
    //
    //        assertAll(() -> assertEquals(
    //                request.metadata(),
    //                nextStage.requestBuilder.metadata()
    //        ));
    //    }
    //
    //    static <R extends ThreadRequest> void messagesRemainsUnchanged(
    //            ThreadConfigurationStage<R> previousStage,
    //            ThreadConfigurationStage<R> nextStage
    //    ) {
    //        assertEquals(
    //                previousStage.requestBuilder.messages(),
    //                nextStage.requestBuilder.messages()
    //        );
    //    }
    //
    //    static <R extends ThreadRequest> void metadataRemainsUnchanged(
    //            ThreadConfigurationStage<R> previousStage,
    //            ThreadConfigurationStage<R> nextStage
    //    ) {
    //        assertEquals(
    //                previousStage.requestBuilder.metadata(),
    //                nextStage.requestBuilder.metadata()
    //        );
    //    }
    //
    //    static void execute(ThreadConfigurationStage<ThreadCreationRequest>
    //    threadConfigurationStage) {
    //        when(threadConfigurationStage.httpExecutor.execute(any()))
    //        .thenAnswer(res -> THREAD_RESPONSE);
    //    }
    //
    //    static void executeWithPathVariable
    //    (ThreadConfigurationStage<ThreadModificationRequest>
    //    threadConfigurationStage) {
    //        when(threadConfigurationStage.httpExecutor
    //        .executeWithPathVariable(
    //                any(),
    //                any()
    //        )).thenAnswer(rest -> THREAD_RESPONSE);
    //    }
}