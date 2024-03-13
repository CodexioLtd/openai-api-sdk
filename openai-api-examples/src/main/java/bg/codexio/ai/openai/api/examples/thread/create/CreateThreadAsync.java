package bg.codexio.ai.openai.api.examples.thread.create;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateThreadAsync {

    public static void main(String[] args) {
        var file = new File(CreateThreadImmediate.class.getClassLoader()
                                                       .getResource("fake-file.txt")
                                                       .getPath());

        Threads.defaults()
               .and()
               .creating()
               .deepConfigure()
               .meta()
               .awareOf(
                       "key1",
                       "value1",
                       "key2",
                       "value2"
               )
               .file()
               .attachAsync(
                       file,
                       config -> config.message()
                                       .startWith("You're java developer.")
                                       .feedAsync(
                                               file,
                                               runtimeSelection -> runtimeSelection.async()
                                                                                   .finishRaw()
                                                                                   .then(System.out::println)
                                       )
               );
    }
}
