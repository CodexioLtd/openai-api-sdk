package bg.codexio.ai.openai.api.examples.thread.create;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateThreadImmediate {

    public static void main(String[] args) {
        var file = new File(CreateThreadImmediate.class.getClassLoader()
                                                       .getResource("fake-file.txt")
                                                       .getPath());

        var thread = Threads.defaults()
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
                            .attachImmediate(file)
                            .message()
                            .startWith("You're java developer.")
                            .feedImmediate(file);

        System.out.println(thread);
    }
}