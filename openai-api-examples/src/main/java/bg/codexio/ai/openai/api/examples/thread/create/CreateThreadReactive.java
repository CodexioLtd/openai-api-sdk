package bg.codexio.ai.openai.api.examples.thread.create;

import bg.codexio.ai.openai.api.sdk.thread.Threads;
import reactor.core.publisher.Mono;

import java.io.File;

public class CreateThreadReactive {

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
               .attachReactive(file)
               .flatMap(config -> Mono.just(config.message()
                                                  .startWith("You're java developer.")
                                                  .feedReactive(file)
                                                  .flatMap(runtimeSelection -> runtimeSelection.reactive()
                                                                                               .finishRaw()
                                                                                               .response())))
               .subscribe(System.out::println);
    }
}
