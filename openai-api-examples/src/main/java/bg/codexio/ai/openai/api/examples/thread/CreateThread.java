package bg.codexio.ai.openai.api.examples.thread;

import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateThread {

    public static void main(String[] args) {
        var file = new File(CreateThread.class.getClassLoader()
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
                            .attach(file)
                            .message()
                            .startWith("You're java developer.")
                            .feed(file);

        System.out.println(thread);
    }
}