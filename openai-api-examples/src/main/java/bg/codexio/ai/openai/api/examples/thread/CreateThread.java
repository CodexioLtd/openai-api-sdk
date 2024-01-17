package bg.codexio.ai.openai.api.examples.thread;

import bg.codexio.ai.openai.api.examples.file.UploadFile;
import bg.codexio.ai.openai.api.sdk.thread.Threads;

import java.io.File;

public class CreateThread {

    public static void main(String[] args) {
        var file = new File(UploadFile.class.getClassLoader()
                                            .getResource("fake-file.txt")
                                            .getPath());

        var emptyThread = Threads.defaults()
                                 .and()
                                 .creating()
                                 .withMeta(
                                         "key1",
                                         "value1",
                                         "key2",
                                         "value2"
                                 )
                                 .startWithToFile("You're java developer")
                                 .feed(file);

        System.out.println(emptyThread);
    }
}