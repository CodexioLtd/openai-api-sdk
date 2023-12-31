# OpenAI SDK by Codexio

[![Maven Central](https://img.shields.io/maven-central/v/bg.codexio.ai/openai-api-sdk.svg)](https://central.sonatype.com/artifact/bg.codexio.ai/openai-api-sdk)
[![Build](https://github.com/CodexioLtd/openai-api-sdk/actions/workflows/maven.yml/badge.svg)](https://github.com/CodexioLtd/openai-api-sdk/actions/workflows/maven.yml)
[![Coverage](https://codecov.io/github/CodexioLtd/openai-api-sdk/graph/badge.svg?token=013OEUIYWI)](https://codecov.io/github/CodexioLtd/openai-api-sdk)
[![License](https://img.shields.io/github/license/CodexioLtd/openai-api-sdk.svg)](https://github.com/CodexioLtd/openai-api-sdk/blob/master/LICENSE)

This library provides a Software Development Kit (SDK) for Java 21+ compliant runtimes
to connect to an OpenAI API and execute both synchronous and asynchronous (via callbacks
or the Reactor Pattern) calls.

It is framework-agnostic, which means that it can run in any environment,
including bare CLI one. But also means, it is **not directly providing** abstractions for
any managed objects in a certain framework such as CDI, EJB, Spring Beans and so on.
Of course, due to its native SDK, objects of any kind are easily created.

## Table of Contents

* [Quick Start](#quick-start)
* [Available SDKs](#available-sdks)
    * [List](#list)
    * [Simple Usage Similarity](#simple-usage-similarity)
    * [Authentication and Model Choosing Similarity](#authentication-and-model-choosing-similarity)
* [Credential Management](#credential-management)
    * [Configuring Authentication](#configuring-authentication)
* [HTTP Context](#http-context)
    * [Fine Grained Control over HTTP Client](#fine-grained-control-over-http-client)
* [HTTP Client Logging](#http-client-logging)
* [AI Models](#ai-models)
* [Runtime Selection](#runtime-selection)
    * [Examples](#examples)
* [Chat API SDK](#chat-api-sdk)
    * [AI Behavior](#ai-behavior)
    * [Manual Configuration](#manual-configuration)
        * [Tokens Configuration](#tokens-configuration)
        * [Tools Configuration](#tools-configuration)
* [Images API SDK](#images-api-sdk)
    * [Creating Images](#creating-images)
        * [Simplified Usage](#simplified-usage)
        * [Model Choosing](#model-choosing)
        * [Styling - **DALL-E-3 only**](#styling---dall-e-3-only)
        * [Quality - **DALL-E-3 only**](#quality---dall-e-3-only)
        * [Choices - **DALL-E-2 only**](#choices---dall-e-2-only)
        * [Size](#size)
        * [Format](#format)
        * [Runtime Selection](#runtime-selection-1)
        * [Prompt](#prompt)
        * [Response Handling](#response-handling)
    * [Editing Images](#editing-images)
        * [Image to Edit](#image-to-edit)
        * [Mask](#mask)
        * [Choices](#choices)
        * [Size](#size-1)
        * [Format](#format-1)
        * [Runtime Selection](#runtime-selection-2)
        * [Prompt](#prompt-1)
        * [Response Handling](#response-handling-1)
    * [Creating Image Variations](#creating-image-variations)
        * [Image to Create Variations for](#image-to-create-variations-for)
        * [Choices](#choices-1)
        * [Size](#size-2)
        * [Format](#format-2)
        * [Runtime Selection](#runtime-selection-3)
        * [Response handling](#response-handling-2)
* [Vision API SDK](#vision-api-sdk)
    * [Simplified Usage](#simplified-usage-1)
    * [Model Choosing](#model-choosing-1)
    * [Tokens Configuration](#tokens-configuration-1)
    * [Image for Explanation](#image-for-explanation)
    * [Image Detail Stage](#image-detail-stage)
    * [Continuous Prompt](#continuous-prompt)
    * [Runtime Selection](#runtime-selection-4)
* [Speech API SDK](#speech-api-sdk)
    * [Simplified Usage](#simplified-usage-2)
    * [Model Choosing](#model-choosing-2)
    * [Speaker Voice](#speaker-voice)
    * [Output Audio Format](#output-audio-format)
    * [Playback Speed](#playback-speed)
    * [Generate Audio File](#generate-audio-file)
* [Transcription API SDK](#transcription-api-sdk)
    * [Simplified Usage](#simplified-usage-3)
    * [Model Choosing](#model-choosing-3)
    * [Uploading Audio File](#uploading-audio-file)
    * [Setting Temperature](#setting-temperature)
    * [Inputting Language](#inputting-language)
    * [Configuring Response Format](#configuring-response-format)
    * [Additional Prompting](#additional-prompting)
* [Translation API SDK](#translation-api-sdk)
    * [Example](#example)
* [Contributing](#contributing)
* [License](#license)

## Quick Start

In this chapter we will explore several examples of using the SDK, so you can start quickly.
In the next chapters you will see an explanation of all key concepts.

1. First of all, make sure you have created an API Key from [here](https://platform.openai.com/api-keys).
2. Create a new Java CLI Project with Maven
3. Add the library as a dependency in your project

```xml
<dependency>
    <groupId>bg.codexio.ai</groupId>
    <artifactId>openai-api-sdk</artifactId>
    <version>0.8.0.BETA</version>
</dependency>
```

- **Optional**: *If you don't need the whole SDK, but for instance only HTTP Clients or Payload Types, you can use
  one (or more) of the following **artfiactId**s:*

    <details>
    <summary>
    Click here to see
    </summary>
    
    | Artifact              | Description                                                                                       |
    |-----------------------|---------------------------------------------------------------------------------------------------|
    | `openai-api-models`   | Contains info only for AI Model namings, such as GPT-4-Preview, DALL-E-2 and so on                |
    | `openai-api-payload`  | Contains Request/Response DTO models and related information                                      |
    | `openai-api-http`     | Contains HTTP Clients such as `ChatHttpExecutor`, `CreateImageHttpExecutor`, etc., ...            |
    | `openai-api-examples` | Usually you do not import this artfiactId, rather you can check the source code for some examples |
    
    </details>

4. Create in your `src/main/resources` folder a file named `openai-credentials.json` with the following content:

```json
{
  "apiKey": "YOUR_API_KEY_FROM_STEP_ONE_HERE"
}
```

5. Create an empty class named `Main` (or however you want)
6. Use the simple initialization through `Chat.simply(...)` as shown below:

```java
public class Main {
    public static void main(String[] args) {
        String answer = Chat.simply()
                            .ask("Can you calculate 2+2?");
        System.out.println(answer);
    }
}
```

7. If you run the program, you will probably see an output that looks like:

```text
Yes, 2 + 2 equals 4.
```

8. Do the same to generate a picture

```java
public class Main {
    public static void main(String[] args) {
        String imageUrl = Images.simply()
                                .generate("Cat holding a Java cup of coffee.");
        System.out.println(imageUrl);
    }
}
```

9. If you run the program, you will probably see a big image URL containing the image, such as:
   [https://oaidalleapiprodscus.blob.core.windows.net/private/org-MS8m...5LXKUDfck4joMsW/vZuM%3D](https://oaidalleapiprodscus.blob.core.windows.net/private/org-MS8mbXlW62IEG98gxxCPirSX/user-6Uq6tTKqkiTRGL9eSP30tKAz/img-iEfjBjQFyqRIwlcLg22XYEvv.png?st=2023-11-28T21%3A00%3A53Z&se=2023-11-28T23%3A00%3A53Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-11-28T05%3A52%3A22Z&ske=2023-11-29T05%3A52%3A22Z&sks=b&skv=2021-08-06&sig=QfxFKQDmxBRPn03SeZ7Ce%2Bq5LXKUDfck4joMsW/vZuM%3D)

![Java Cat](https://i.ibb.co/7nzhpvr/scaled-java-cat-min.png)

This is the simplest way you can use the SDK. Below you will find some tips how to
get started and in the following chapters, an explanation of the key concepts and more
examples.

Feel free to play with the `Chat`, `Images`, `Vision`, `Speech`
`Transcription` or `Translation` objects and adjust settings.
There's a decent fluent interface that will help you choose your settings.
For example:

```java
public class Main {
    public static void main(String[] args) {
        String answer = Chat.authenticate(FromJson.AUTH)
                            .and()
                            .poweredByGPT40()
                            .inventive()
                            .noExcuses()
                            .ask("Write me a metal song about how cool Java is.");

        System.out.println(answer);
    }
}
```

And, Whoa-la! There's your song:
<details>
<summary>
<pre>
In the realm of digits, in the land of code,
There's a beast named Java, and its ...
</pre>
</summary>

```text
... tale must be told.
It's not a simple script, it's not just lines on screen,
It's the pulse of machines, it's the coder's dream.

(Chorus)
Java! The language of the brave,
In the world of zeros and ones, it's the knight in the cave.
Java! Powering all in sight,
From the smallest devices to networks of light.

(Verse 2)
With its robust platform, and its object-oriented might,
It stands unbroken, in the darkest night.
Creating applets, applications galore,
With its vast libraries, it continues to explore.

(Chorus)
Java! The titan of the code,
In a world that's binary, it's the open road.
Java! Where logic intertwines,
It's the architect of the modern times.

(Bridge)
From software to web space, from mobile to game,
Java is the magic behind every frame.
With its bytecode high, and its garbage collection neat,
In the realm of coding, Java is elite.

(Chorus)
Java! The wizard of the tech,
With its secure framework, it stands erect.
Java! In every coder's creed,
From silicon valleys to the cyberspace indeed.

(Outro)
So here's to Java, the king in the digital mist,
In the symphony of algorithms, your presence persists.
With your threads and your exceptions, so versatile,
Java, you make every byte worthwhile.
```

</details>

## Available SDKs

### List

- **Chat** - implemented
  by `bg.codexio.ai.openai.api.sdk.chat.Chat` ([OpenAI Docs](https://platform.openai.com/docs/api-reference/chat/create))
- **Images** - implemented
  by `bg.codexio.ai.openai.api.images.Images` ([OpenAI Docs](https://platform.openai.com/docs/api-reference/images/create))
- **Vision** - implemented
  by `bg.codexio.ai.openai.api.vision.Vision` ([OpenAI Docs](https://platform.openai.com/docs/guides/vision))
- **Speech** - implemented
  by `bg.codexio.ai.openai.api.voice.speech.Speech` ([OpenAI Docs](https://platform.openai.com/docs/api-reference/audio/createSpeech))
- **Transcription** - implemented
  by `bg.codexio.ai.openai.api.voice.transcription.Transcription` ([OpenAI Docs](https://platform.openai.com/docs/api-reference/audio/createTranscription))
- **Translation** - implemented
  by `bg.codexio.ai.openai.api.voice.translation.Translation` ([OpenAI Docs](https://platform.openai.com/docs/api-reference/audio/createTranslation))

All SDKs have consistent interface over credentials management and AI Model Choosing and
start to differ in the settings that are more naturally associated to one over the another.

### Simple Usage Similarity

```java
public class Main {
    public static void main(String[] args) {
        String answer = Chat.simply()
                            .ask("How much is 2+2?");
        String imageUrl = Images.simply()
                                .generate("Cat holding a Java cup of coffee.");
    }
}
```

### Authentication and Model Choosing Similarity

```java
public class Main {
    public static void main(String[] args) {
        TemperatureStage temperatureStage = Chat.authenticate(FromJson.AUTH)
                                                .and()
                                                .poweredByGPT40();

        FormatStage formatStage = Images.authenticate(FromJson.AUTH)
                                        .and()
                                        .poweredByDallE3();

        TokenStage tokenStage = Vision.authenticate(FromJson.AUTH)
                                      .and()
                                      .poweredByGpt40Vision();

        VoiceStage voiceStage = Speech.authenticate(FromJson.AUTH)
                                      .and()
                                      .hdPowered();

        AudioFileStage audioFileStage = Transcription.authenticate(FromJson.AUTH)
                                                     .and()
                                                     .poweredByWhisper10();
    }
}
```

The APIs start to differ after you choose the model, but the fluent interface will guide you
how to proceed further. And, of course, the documentation below :)

## Credential Management

Credentials are passed to the SDK via instantiating the `openai-api-payload/ApiCredentials`
object, which looks like:

```java
public record ApiCredentials(
        String apiKey,
        String organization,
        String baseUrl
) {}
```

Where `organization` and `baseUrl` are not mandatory, it will fall back to no specific
organization and to the default URL of OpenAI API if none are present. `apiKey` is mandatory though.

If you are not sure what an `apiKey` is, follow the official OpenAI documentation and
create one from [here](https://platform.openai.com/api-keys).

The `openai-api-sdk/SdkAuth` interface acts as an abstract factory for credential
management. There are three known implementations in the SDK right now, but you are free
to add more if needed (for instance, implementation that reads from Spring's `application.yml`).
Current implementations:

- `openai-api-sdk/FromEnvironment` - used as `FromEnvironment.AUTH` which scans for
  environment variables `OPENAI_API_BASE_URL`, `OPENAI_API_KEY` and `OPENAI_ORG_ID`.
    * All available environment variables that you can pass can be found
      in `openai-api-payload/AvailableEnvironmentVariables`
- `openai-api-sdk/FromJson` - used as follows:
    * `FromJson.AUTH` - which is going to read a JSON file in the `resources` folder called `openai-credentials.json`
    * `FromJson.resource(fileName)` - which is going to read the passed as argument JSON file in the `resources` folder
    * `FromJson.file(fileName)` - which is going to read a file as an absolute path
    * The only mandatory key in the JSON is `apiKey`. You can find all possible keys
      in `openai-api-sdk/FromJson.AvailableKeys`
- `openai-api-sdk/FromDeveloper` - used as `FromDeveloper.doPass(apiCredentialsInstance)` - this is not a
  recommended way to do it, as you may expose credentials in the code-base. Always use this very
  cautiously and pass them after being read from a secure storage. Or use this factory only
  for testing purposes.

### Configuring Authentication

The authentication factory has to be passed to the `Chat.authenticate(...)` method such
as `Chat.authenticate(FromJson.AUTH)`. You can also use `Chat.simply()` which will scan
the available factories and try them one by one until one of them succeeds without
exception. However, the `simply()` method configures the API in very limited fashion.

## HTTP Context

`Chat.authenticate(SdkAuth)` returns an `HttpBuilder` where you can configure the timeouts,
of the underlying HTTP Client. You can configure them directly alongside the credentials
if you use `Chat.authenticate(HttpExecutorContext)` overload. In the stage of `HttpBuilder`,
you can add an `ObjectMapper` via the `understanding(ObjectMapper)` method or leave it
as it is and call `and()` to proceed further.

### Fine Grained Control over HTTP Client

The methods above do not give you fine-grained control over the underlying HTTP Client.
For instance if you want to configure connection pooling, interceptors or logging, you can
provide your own instance of the HTTP Client.

Currently, we are using Square's `OkHttp` client. Each subclass of `DefaultOpenAIHttpExecutor`
accepts an instance of the `OkHttpClient`. The idea of the above methods is to receive
and instance of underlying `ChatHttpExecutor` via configurations. But if you feel comfortable,
you can instantiate your own `ChatHttpExecutor` by providing `OkHttpClient, baseUrl, ObjectMapper`.

This can be done by invoking `Chat.throughHttp(new ChatHttpExecutor(client, url, mapper))`.
For example:

```java
public class Main {
    public static void main(String[] args) {
        String answer = Chat.throughHttp(new ChatHttpExecutor(
                                    new OkHttpClient.Builder().connectionPool(new ConnectionPool(
                                                                      5,
                                                                      5,
                                                                      TimeUnit.MINUTES
                                                              ))
                                                              .build(),
                                    "https://your-custom-openai-url.com/api/v1",
                                    new ObjectMapper()
                            ))
                            .turboPowered()
                            .deterministic()
                            .developer("Java")
                            .andRespond()
                            .immediate()
                            .ask("Is Java the best?");

        System.out.println(answer);
    }
}
```

## HTTP Client Logging

There are several points where requests and responses are being logged. Here's a list:

- After Request DTO is converted to JSON and before being sent further:

```java
log(
        "Incoming request to {}{} with body: {}",
                this.
        baseUrl,
        this.
        resourceUri,
        json
        );
```

- If error response is received, the RAW response is logged before being converted to an Error DTO:

```java
log(
        "Received error response: {}",
        response
        );
```

- When response body is consumed as a string and before being converted to a Response DTO
  (this usually occurs twice, as its logged in two methods):

```java
log(
        "Received raw response: {}",
        body
        );
```

Logging is not enabled by default. You can enable it by supplying an environment variable on startup named:
`OPENAI_LOGGING_ENABLED`. It's value does not matter unless it's not `null`, but usually it would be better to do it as
`OPENAI_LOGGING_ENABLED=true`.

If logging is enabled, by default it logs as `log.debug(...)`. However, if you want to control the log level, you
can additionally supply an environment variable named `OPENAI_LOGGING_LEVEL` such as `OPENAI_LOGGING_LEVEL=info`.

All logs contain in them a unique identifier such as `[unique-id-here]: some log message`. This is used as a
correlation id, so you can easily track which response or error to which request is related.

Keep in mind that no logging library is provided by the SDK, only the facade of SLF4J. You are advised to
add implementation on your own in the consuming project. Take a look in the `openai-api-examples` module for
such example.

## AI Models

Once you configure the HTTP Context either via `Chat.throughHttp(...)` or via
`Chat.authenticate(...).and()` (similarly `Images.throughHttp/authenticate`),
you are faced into the next configuration stage - which AI model to use.
You can use `poweredBy(ModelType)` to specify the model.

`ModelType` is not an enumeration, but rather a functional interface which
implementations supply a `name()`. Currently, there are several `GPT3.5`, `GPT4`,
`DALL-E`, `TTS` and `Whisper` models that are implemented as classes:

- gpt-4-0613
- gpt-4-32k-0613
- gpt-4-1106-preview
- gpt-3.5-turbo-0613
- gpt-3.5-turbo-1106
- dall-e-3 (Currently, OpenAI restricts this model only for image creation, cannot be used for modification)
- dall-e-2
- tts-1
- tts-1-hd
- whisper-1

Full endpoint/model compatibility matrix can be
found [here](https://platform.openai.com/docs/models/model-endpoint-compatibility).

You can use AI Models by providing an instance in several ways:

- Physical class instance - `Chat.authenticate(...).and().poweredBy(new GPT40Model())`
- Anonymous instance - `Chat.authenticate(...).and().poweredBy(() -> "gpt-4"))`
- Factory method - `Chat.authenticate(...).and().poweredByGPT40()`
- Since the `ModelType` is not an enumeration, but an interface,
  you can easily create new classes and supply their objects

## Runtime Selection

All SDKs as a last step (if `simply()` is not used) provide the ability to choose the
underlying execution runtime - whether the requests to be sent in a blocking synchronous
fashion (`immediate()`) or delayed for further notice in an asynchronous fashion (`async()` and `reactive()`).

Usually, the code chain ends in the following manner:

```java
public class Main {
    public static void main(String[] args) {
        var response = RuntimeSelectionStage.andRespond()
                                            .immediate()
                                            .specificMethodForSendingRequest();

        doSomethingWith(response);

        RuntimeSelectionStage.andRespond()
                             .async()
                             .specificMethodForSendingRequest()
                             .then(response -> doSomethingWith(response));

        RuntimeSelectionStage.andRespond()
                             .reactive()
                             .specificMethodForSendingRequest()
                             .subscribe(response -> doSomethingWith(response));
    }
}
```

- `immediate()` - prepares the requests to be sent and to wait for a response. The response object
  is materialized directly, so you can use it in a traditional imperative codebase.
- `async()` - prepares the requests to be sent and to notify for a response in further provided
  callbacks. Most of the APIs support two subscription promises - one for a stringified line of
  the response (not recommended, but gives a lot of control if necessary) and another for
  the whole response. Usually with `async().method().onEachLine(line -> { ... })` and
  `async().method().thne(response -> { ... })`. In Java 21 it
  uses [Virtual Threads](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-DC4306FC-D6C1-4BCC-AECE-48C32C1A8DAA)
  thanks to the
  underlying [Square's OkHttp Client](https://square.github.io/okhttp/changelogs/changelog/#version-500-alpha12).
- `reactive()` - mostly the same as `async()`, but utilizes the `Mono<T>` and `Flux<T>`
  patterns from Project Reactor. **This is only recommended if your codebase is already
  fully reactive, using something like `Reactor Netty`**. Otherwise, the overhead of
  spawning worker threads for the reactive execution may reduce significantly the benefit.

### Examples

Here are several examples with different SDKs and different ways of calling `async()` or `reactive()`:

```java
public class Main {
    public static void main(String[] args) {
        // Chat Async
        Chat.defaults()
            .and()
            .poweredByGPT40()
            .deterministic()
            .andRespond()
            .async() // starts here
            .ask("2+2?")
            .then(System.out::println);

        // Chat Reactive
        Chat.defaults()
            .and()
            .poweredByGPT40()
            .deterministic()
            .andRespond()
            .reactive() // starts here
            .ask("2+2?")
            .subscribe(System.out::println);

        // Images Async
        Images.defaults()
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectUrl()
              .andRespond()
              .async()
              .instruct("ginger cat enjoying cinnamon rolls and a cup of coffee")
              .whenDownloaded(
                      new File("var/tmp/targetFolder"),
                      files -> System.out.println(files[0].getAbsoluteFile())
              );

        // Images Reactive
        Images.defaults()
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectUrl()
              .andRespond()
              .reactive()
              .generate("Ginger cat enjoying cinnamon rolls and a cup of coffee")
              .download(new File("var/tmp/targetFolder"))
              .subscribe(file -> System.out.println(file.getAbsoluteFile()));

        // Speech Async - then(response -> { ... })
        Speech.defaults()
              .and()
              .hdPowered()
              .defaultSpeaker()
              .mp3()
              .fast()
              .async() // starts here
              .dictate("There is a story to tell.")
              .whenDownloadedTo(new File("/var/tmp/targetFolder"))
              .then(downloadedFile -> System.out.println(downloadedFile.getAbsolutePath()));

        // Speech Async - onEachLine(line -> { ... })
        Speech.defaults()
              .and()
              .hdPowered()
              .defaultSpeaker()
              .mp3()
              .fast()
              .async() // starts here
              .dictate("There is a story to tell.")
              .whenDownloadedTo(new File("/var/tmp/targetFolder"))
              .onEachLine(binaryStringResponseLine -> System.out.println(binaryStringResponseLine.length()));

        // Speech Reactive
        Speech.defaults()
              .and()
              .hdPowered()
              .defaultSpeaker()
              .mp3()
              .fast()
              .reactive() // starts here
              .dictate("There is a story to tell.")
              .downloadTo(new File("/var/tmp/targetFolder"))
              .subscribe(downloadedFile -> System.out.println(downloadedFile.getAbsolutePath()));

        // Transcription Async
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic()
                     .talkingInEnglish()
                     .subtitles()
                     .async() // starts here
                     .guide("This is my graduate speech.")
                     .then(response -> System.out.println(response.text()));

        // Transcription Reactive
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic()
                     .talkingInEnglish()
                     .subtitles()
                     .reactive() // starts here
                     .guide("This is my graduate speech.")
                     .response()
                     .subscribe(response -> System.out.println(response.text()));
    }
}
```

For simplicity all other examples in the SDKs below will use only the `immediate()` runtime. The only thing you need to
know is that the same behavior of `immediate()` is replicated for `async()` and `reactive()` by utilizing promises
or reactor objects.

## Chat API SDK

### AI Behavior

The behavior of the model is dictated by several parameters in conjunction with
the messages you provide it. Usually you have to control the entropy that is caused there
by providing either a **temperature or a nucleus sampling** (you can also provide both, but
it's less recommended). In other words you may control the creativity of the answers -
more creative answers could lead to an inception of new concepts, however, most likely,
they are less factual. Creativity can be encouraged for songwriting, for instance, but
may not be encouraged for mathematical concepts and software development.

Sometimes the answer also may sound a little bit clumsy, like they are written by an
illiterate with a lot of repetition of words and phrases. Also, when you ask a subsequent
question, you may kind of receive the same answer obfuscated in different words. This
is also controllable by **presence and frequency** penalties. Usually, but not mandatory,
they are an inverse function of the temperature.

You have several ways to control this with the SDK. The one is pretty manual, while you
are still given the option to use the enumerations for `Creativity` (temperature, top_p) and
`RepetitionPenalty` (presence and frequency penalty).

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .creativeAs(Creativity.INTRODUCE_SOME_RANDOMNESS);
    }
}
```

You can check the value of the `Creativity` enumeration, this example essentially puts `0.2`,
which means very less randomness, but still some. It will affect temperature and nothing else.

Of course, you can try to instruct the SDK to deduct the values for the other values,
when you supply the `Creativity`. Essentially, it will add some relatively close `top_p` to
the temperature (less for smaller values, more for bigger values) and inverted value
for the penalties (e.g. `0.8` for `0.2` creativity). To do so, use `scaleRepetitionToCreativity(Creativity)`:

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .scaleRepetitionToCreativity(Creativity.INTRODUCE_SOME_RANDOMNESS);
    }
}
```

Or, you can leave us to decide based on what you want, such as:

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .imaginative();
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .predictable();
    }
}
```

The values behind the fluent interface may change with the versions of the SDK,
to adjust more to what they are meant to.

As a prelude to the fine-grained manual configuration, you can use the `deepConfigure()`
method to invoke changes in the accuracy as well:

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .deepConfigure()
            .accuracy() // accuracy configuration
            .withFrequencyPenalty(ALMOST_GOOD)
            .withPresencePenalty(ALMOST_GOOD)
            .withTemperature(TRULY_DETERMINISTIC)
            .withSamplingProbability(TRULY_DETERMINISTIC)
            .and()
            .done();
    }
}
```

### Manual Configuration

The `deepConfigure()` method gives you a possibility to adjust not only the `accuracy`,
but also the `tokens` and the `tools`.

#### Tokens Configuration

You can adjust the restriction of how many tokens should be provided (to reduce costs),
when the AI shall stop generating and how many answers it should generate:

```java
public class Main {
    public static void main(String[] args) {
        Chat.authenticate(FromJson.AUTH)
            .and()
            .poweredByGPT40()
            .deepConfigure()
            .accuracy()
            .withFrequencyPenalty(ALMOST_GOOD)
            .withPresencePenalty(ALMOST_GOOD)
            .withTemperature(TRULY_DETERMINISTIC)
            .withSamplingProbability(TRULY_DETERMINISTIC)
            .and()
            .tokens() // tokens configuration
            .max(3000)
            .stopAt(
                    "That's all folks",
                    "Fine!"
            )
            .choices(2)
            .and()
            .done();
    }
}
```

#### Tools Configuration

You can prepare your AI with a set of tools and even instruct it which tool to use.
Tools are specific prepared operations that you the AI will compile for you and use
them whenever it deducts they have a good vector of usage or if you instruct it to use one.

In the current implementation, the only type of tool that exists is a `function`.
Functions are named pieces which the AI can deduct to prepare output for. Currently,
it does no magic, but only outputs a prepared JSON that you can use to call a function,
either in your code or from a remote API. Common misunderstanding is that you can define
a function such as `getCapitalCity(country)` and it will be executed, returning you the
actual capital city. It does not work this way, but rather the AI will return you a JSON,
that you can use to call a real function that is doing the job, if you have such. Let us
give you below a worthy example:

Imagine you want to call Google Maps Places API for nearby search. You have to send a
request such
as `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362&radius=1500&type=restaurant`
or in other words to search for restaurants in the radius of 1.5km in some geo coordinates.
OpenAI API cannot give you up-to-date suggestions for such places, but can prepare for you
a request that you can send to Google Maps API by a normal chat prompt. Here is how:

```java
public class Main {
    public static void main(String[] args) {
        String answer = Chat.authenticate(FromJson.AUTH)
                            .and()
                            .poweredByGPT40()
                            .deepConfigure()
                            .tools()
                            .chooseTool(GetNearbyPlaces.FUNCTION)
                            .and()
                            .done()
                            .andRespond()
                            .immediate()
                            .ask("Restaurants in Sofia 10 min by walk in city centre.");

        System.out.println(answer);
    }
}
```

And the output is:

```json
{
  "type": "restaurant",
  "location": "42.6977,23.3219",
  "radius": 800
}
```

Now you can use this JSON to call Google Maps Places API and show your users the desired
information.

The function `GetNearbyPlaces` is one of the reference implementations we have provided, so you
can do the same fashion your own functions as well. Here is the code of `GetNearbyPlaces`:

```java
public class GetNearbyPlaces
        extends ChatFunction {

    public static final FunctionTool FUNCTION = new FunctionTool(new GetNearbyPlaces());
    public static final FunctionTool.FunctionToolChoice CHOICE = new FunctionTool.FunctionToolChoice(new GetNearbyPlacesFunctionChoice());

    private GetNearbyPlaces() {
        super(
                "Gets the nearby places of a given type, in a certain radius for a geolocation.",
                "getNearbyPlaces",
                Map.of(
                        "type",
                        "object",
                        "properties",
                        Map.of(
                                "location",
                                Map.of(
                                        "type",
                                        "string",
                                        "description",
                                        "Comma separated geo coordinates such as 45,6789,12.3456"
                                ),
                                "radius",
                                Map.of(
                                        "type",
                                        "number",
                                        "description",
                                        "Radius in meters such as 1500 for 1.5km"
                                ),
                                "type",
                                Map.of(
                                        "type",
                                        "string",
                                        "description",
                                        "Place type such as gas station, restaurant, hotel, atm, etc."
                                )
                        )
                ),
                GetNearbyPlaces.CHOICE
        );
    }

    public record GetNearbyPlacesFunctionChoice()
            implements FunctionChoice {
        @Override
        public String name() {
            return GetNearbyPlaces.FUNCTION.function()
                                           .getName();
        }
    }
}
```

Essentially, a function is a class that defines a name, possibly a description and a map of parameters.

## Images API SDK

Images API gives three different possibilities for working with visualizations, all of them providing responses with new
generated images. The current actions that can be performed are image generation based only on prompt, image editing
based on an input image and a prompt and generation of image variations based only on an image input.

For editing or generating variations, it's mandatory to configure the input images and the prompt. For image creation,
you have
the possibility to make a wider configuration. Of course, the SDK also provides a simplified way where the images
can be generated only given a text instruction.

The action is chosen after the authentication step from the fluent interface.

### Creating Images

#### Simplified Usage

Use `simply()` to bypass the optional configuration and jump directly to providing a text:

```java
public class Main {
    public static void main(String[] args) {
        String imageUrl = Images.simply()
                                .generate("Ginger cat enjoying a cinnamon roll and a cup of coffee");
    }
}
```

Below you can find how to configure all the optional inputs.

#### Model Choosing

Model can be either [DALL-E-2](https://openai.com/dall-e-2) or [DALL-E-3](https://openai.com/dall-e-3),
provided by the following fluent methods:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE2();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3();
    }
}
```

The SDK also provides a `defaultModel()` method that fallbacks to DALL-E-2.

Keep in mind that using the two different models comes with visible differences in the output result and also in the
configuration possibilities. While DALL-E-2 provides more straightforward and cartoon-ish looking results,
DALL-E-3 has more vivid and realistic looking results.

Because of that, DALL-E-3 has two more configuration possibilities than DALL-E-2. Quality and style can be manually
modified in order to achieve the expected results and to
toggle the level of creativity. On the other side, the OpenAI API currently limits the count of the generated images to
one only for Dall-e-3.

Because of the specifics of the model, the next configuration step is different for the 2 separate models.

#### Styling - **DALL-E-3 only**

Style can be either `vivid()`:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid();
    }
}
```

or `natural()`:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .natural();
    }
}
```

Choosing the first option will encourage the creativity and the abstraction of the generated result, so it's suitable
for generating art, for example. If you want to have more straightforward and realistic-looking results, then the
natural
option will be a good choice.

The SDK has also `unstyled()` option that fallbacks to the natural style.

#### Quality - **DALL-E-3 only**

The quality of the image is also configurable for DALL-E-3, providing two options:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .standardQuality();
    }
}
```

Using the first option will create images with finer details and greater consistency across the image.

#### Choices - **DALL-E-2 only**

If you've chosen the DALL-E-2 model, the next configuration step will be to configure the number of the generated
output.
Keep in mind the max count supported by the OpenAI API is **10**.

You can set the number of choices using:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE2()
              .withChoices(5);
    }
}
```

or fallback to a single choice:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE2()
              .singleChoice();
    }
}
```

#### Size

The size of the output image is configurable for both models, however the options are different, as DALL-E-2 is limited
only to square forms.

The possible options for DALL-E-2 include:

| Method           | Description                                      |
|------------------|--------------------------------------------------|
| `bigSquare()`    | ![1024x1024](https://placehold.co/1024x1024.png) |
| `mediumSquare()` | ![512x512](https://placehold.co/512x512.png)     |
| `smallSquare()`  | ![256x256](https://placehold.co/256x256.png)     |

In this case, an example configuration will look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE2()
              .singleChoice()
              .bigSquare();
    }
}
```

For DALL-E-3 there is a choice for the form of the image, but the exact dimensions are not configurable. The possible
options are:

| Method        | Description                                      |
|---------------|--------------------------------------------------|
| `square()`    | ![1024x1792](https://placehold.co/1024x1024.png) |
| `landscape()` | ![1792x1024](https://placehold.co/1792x1024.png) |
| `portrait()`  | ![1024x1024](https://placehold.co/1024x1792.png) |

In this case, an example configuration will look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait();
    }
}
```

#### Format

For both models, the format of the output image is configurable between the url and Base 64 options. Please keep in mind
that
the url output contains a temporary url and the contained images are deleted after a few hours. If you want to save the
image,
then you have to download it once the response is received.

The format configuration has the following options:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectUrl();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64();
    }
}
```

#### Runtime Selection

Reaching the `andRespond()` method means that the configuration of the response is finished and tou have to choose one
of the runtimes, already explained in details in this document. Basically, the three options look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .immediate();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .async();
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .reactive();
    }
}
```

#### Prompt

Once the runtime is selected, it's time for the final and most important field - the prompt for the model.
The prompt is set using the `generate()` method, for example:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .immediate()
              .generate("Ginger cat enjoying cinnamon rolls and a cup of coffee");
    }
}
```

#### Response Handling

The output can be either returned as `ImageDataReponse` object or a file downloaded in a location by choice,
respectively:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .immediate()
              .generate("Ginger can enjoying cinnamon rolls and a cup of coffee")
              .andGetRaw();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE3()
              .vivid()
              .highDefinitioned()
              .portrait()
              .expectBase64()
              .andRespond()
              .immediate()
              .generate("Ginger can enjoying cinnamon rolls and a cup of coffee")
              .andDownload(new File("/var/target/folder"));
    }
}
```

### Editing Images

Editing images is currently supported only by DALL-E-2, so there are no model configurations available. Instead, the SDK
directly moves to the image editing specific steps:

#### Image to Edit

The image for edit must be **a valid PNG file, less than 4MB, and square. If mask is not provided, image must have
transparency, which will be used as the mask.**
The file is mandatory and is set using:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"));
    }
}
```

#### Mask

To successfully edit an image, it should either have transparent areas or a mask should be provided that has exactly the
same size
as the input image. If you have a mask to provide, then all you need to do is:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(new File("/var/images/mask.png"));
    }
}
```

However, if you don't have a mask available, the SDK gives a variety of options to edit the original image so
it has the needed transparent areas. You can do that by providing a color and/or a `ColorDeviation` value.

Basically, what the SDK does is detecting the input color in the input image and making it transparent. The similarity
of the colors
that are made transparent can be measured using the `ColorDeviation` enum provided by the SDK. The color can be set
using a decimal, hexadecimal or RGB value
or by choosing one of the predefined colors in the `PopularColor` enum.

Currently, the masking enums have the following values

`Color Deviation`

| Value                                     | Description                                                                       |
|-------------------------------------------|-----------------------------------------------------------------------------------|
| `ColorDeviation.THE_SAME`                 | `0.0` - only exactly the same colors like the input color will be transparentized |
| `ColorDeviation.SMALL_DIFFERENCE`         | `0.05`                                                                            |
| `ColorDeviation.BALANCED_TOLERANCE`       | `0.1`                                                                             |
| `ColorDeviation.VISIBLE_DIFFERENCE`       | `0.15`                                                                            |
| `ColorDeviation.TWENTY_PERCENT`           | `0.2`                                                                             |
| `ColorDeviation.THIRTY_PERCENT`           | `0.3`                                                                             |
| `ColorDeviation.HALF`                     | `0.5`                                                                             |
| `ColorDeviation.ALMOST_THE_WHOLE_PICTURE` | `0.75`                                                                            |
| `ColorDeviation.EVERYTHING`               | `0.1` - the whole picture will be transparent regardless of the color             |

`PopularColor`:

| Color                     | Visualization                                               |
|---------------------------|-------------------------------------------------------------|
| `PopularColor.RED`        | ![Red](https://placehold.co/75x15/FF0000/FF0000.png)        |
| `PopularColor.GREEN`      | ![Green](https://placehold.co/75x15/008000/008000.png)      |
| `PopularColor.BLUE`       | ![Blue](https://placehold.co/75x15/0000FF/0000FF.png)       |
| `PopularColor.YELLOW`     | ![Yellow](https://placehold.co/75x15/FFFF00/FFFF00.png)     |
| `PopularColor.PURPLE`     | ![Purple](https://placehold.co/75x15/800080/800080.png)     |
| `PopularColor.CYAN`       | ![Cyan](https://placehold.co/75x15/00FFFF/00FFFF.png)       |
| `PopularColor.MAGENTA`    | ![Magenta](https://placehold.co/75x15/FF00FF/FF00FF.png)    |
| `PopularColor.ORANGE`     | ![Orange](https://placehold.co/75x15/FFA500/FFA500.png)     |
| `PopularColor.PINK`       | ![Pink](https://placehold.co/75x15/FFC0CB/FFC0CB.png)       |
| `PopularColor.BROWN`      | ![Brown](https://placehold.co/75x15/A52A2A/A52A2A.png)      |
| `PopularColor.BLACK`      | ![Black](https://placehold.co/75x15/000000/000000.png)      |
| `PopularColor.WHITE`      | ![White](https://placehold.co/75x15/FFFFFF/FFFFFF.png)      |
| `PopularColor.GRAY`       | ![Gray](https://placehold.co/75x15/808080/808080.png)       |
| `PopularColor.LIGHT_GRAY` | ![Light Gray](https://placehold.co/75x15/D3D3D3/D3D3D3.png) |
| `PopularColor.DARK_GRAY`  | ![Dark Gray](https://placehold.co/75x15/A9A9A9/A9A9A9.png)  |
| `PopularColor.LIME`       | ![Lime](https://placehold.co/75x15/00FF00/00FF00.png)       |
| `PopularColor.MAROON`     | ![Maroon](https://placehold.co/75x15/800000/800000.png)     |
| `PopularColor.OLIVE`      | ![Olive](https://placehold.co/75x15/808000/808000.png)      |
| `PopularColor.NAVY`       | ![Navy](https://placehold.co/75x15/000080/000080.png)       |
| `PopularColor.TEAL`       | ![Teal](https://placehold.co/75x15/008080/008080.png)       |
| `PopularColor.AQUA`       | ![Aqua](https://placehold.co/75x15/00FFFF/00FFFF.png)       |
| `PopularColor.SILVER`     | ![Silver](https://placehold.co/75x15/C0C0C0/C0C0C0.png)     |
| `PopularColor.GOLD`       | ![Gold](https://placehold.co/75x15/FFD700/FFD700.png)       |
| `PopularColor.SKY_BLUE`   | ![Sky Blue](https://placehold.co/75x15/87CEEB/87CEEB.png)   |
| `PopularColor.CORAL`      | ![Coral](https://placehold.co/75x15/FF7F50/FF7F50.png)      |
| `PopularColor.VIOLET`     | ![Violet](https://placehold.co/75x15/8A2BE2/8A2BE2.png)     |
| `PopularColor.INDIGO`     | ![Indigo](https://placehold.co/75x15/4B0082/4B0082.png)     |
| `PopularColor.BEIGE`      | ![Beige](https://placehold.co/75x15/F5F5DC/F5F5DC.png)      |
| `PopularColor.MINT`       | ![Mint](https://placehold.co/75x15/98FB98/98FB98.png)       |
| `PopularColor.LAVENDER`   | ![Lavender](https://placehold.co/75x15/E6E6FA/E6E6FA.png)   |

The `masked()` methods receives a wide variety of inputs, you can choose the most suitable for your needs.
The `unmasked()` method is also one of the default abstractions of masked, that relies on already given a proper input
image.

| Method Signature                                                 | Description                                                                                                                                                        |
|------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `masked(File mask)`                                              | Sets a custom mask from the provided file. The mask must be a valid PNG file, less than 4MB, and have the same dimensions as the original image.                   |
| `masked(int alphaZeroHex, ColorDeviation deviation)`             | Sets the editable areas based on the provided color (in hexadecimal) and deviation level. If the image lacks an alpha channel, it is modified accordingly.         |
| `masked(PopularColor color, ColorDeviation deviation)`           | Sets the editable areas based on a predefined color (from PopularColor enum) and deviation level. If the image lacks an alpha channel, it is modified accordingly. |
| `masked(PopularColor color)`                                     | Sets the editable areas based on a predefined color with the lowest deviation, marking only the specific color for editing.                                        |
| `masked(int alphaZeroHex)`                                       | Sets the editable areas based on the provided color (in hexadecimal) with the lowest deviation, marking only the specific color for editing.                       |
| `masked(int red, int green, int blue, ColorDeviation deviation)` | Sets the editable areas based on RGB values and a deviation level. If the image lacks an alpha channel, it is modified accordingly.                                |
| `masked(int red, int green, int blue)`                           | Sets the editable areas based on RGB values with the lowest deviation, marking only the specific color for editing.                                                |
| `masked(String colorHex, ColorDeviation deviation )`             | Sets the editable areas based on the provided color (in hexadecimal string) and deviation level. If the image lacks an alpha channel, it is modified accordingly.  |
| `masked(String colorHex)`                                        | Sets the editable areas based on the provided color (in hexadecimal string) with the lowest deviation, marking only the specific color for editing.                |
| `smallTransparency()`                                            | Similar to `unmasked` but handles more shades of white, expanding the editable areas with a small color deviation.                                                 |
| `unmasked()`                                                     | Skips the masking stage, setting editable areas based on the predefined white color.                                                                               |

And here is an example of using one of those possibilities to set a mask:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              );
    }
}
```

#### Choices

The next configuration step will be to configure the number of the generated output.
Keep in mind the max count supported by the OpenAI API is **10**.

You can set the number of choices using:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice();
    }
}
```

or fallback to a single choice:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .creating()
              .poweredByDallE2()
              .singleChoice();
    }
}
```

#### Size

The size of the output image is configurable. However, DALL-E-2 is limited only to square forms.

The possible options for the square size include:

| Method           | Description                                      |
|------------------|--------------------------------------------------|
| `bigSquare()`    | ![1024x1024](https://placehold.co/1024x1024.png) |
| `mediumSquare()` | ![512x512](https://placehold.co/512x512.png)     |
| `smallSquare()`  | ![256x256](https://placehold.co/256x256.png)     |

In this case, an example configuration will look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare();
    }
}
```

#### Format

The format of the output image is configurable between the url and Base 64 options. Please keep in mind that
the url output contains a temporary url and the contained images are deleted after a few hours. If you want to save the
image,
then you have to download it once the response is received.

The format configuration has the following options:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectBase64();
    }
}
```

#### Runtime Selection

Reaching the `andRespond()` method means that the configuration of the response is finished and tou have to choose one
of the runtimes, already explained in details in this document. Basically, the three options look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .immediate();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .async();
    }
}

```

or

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .reactive();
    }
}
```

#### Prompt

Once the runtime is selected, it's time for the final and most important field - the prompt for the model.
The prompt is set using the `generate()` method, for example:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .immediate()
              .generate("Ginger cat enjoying cinnamon rolls and a very big cup of coffee");
    }
}
```

#### Response Handling

The output can be either returned as `ImageDataReponse` object or a file downloaded in a location by choice,
respectively:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .immediate()
              .generate("Ginger cat enjoying cinnamon rolls and a very big cup of coffee")
              .andGetRaw();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .editing(new File("/var/images/image.png"))
              .masked(
                      PopularColor.PURPLE,
                      ColorDeviation.BALANCED_TOLERANCE
              )
              .singleChoice()
              .mediumSquare()
              .expectUrl()
              .andRespond()
              .immediate()
              .generate("Ginger cat enjoying cinnamon rolls and a very big cup of coffee")
              .andDownload(new File("/var/target/folder"));
    }
}
```

### Creating Image Variations

Creating image variations is currently supported only by DALL-E-2, so there are no model configurations available.
Instead, the SDK
directly moves to the variation creation specific steps:

#### Image to Create Variations for

The image for edit must be **a valid PNG file, less than 4MB, and square.**
The file is mandatory and is set using:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"));
    }
}
```

#### Choices

The next configuration step will be to configure the number of the generated output.
Keep in mind the max count supported by the OpenAI API is **10**.

You can set the number of choices using:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5);
    }
}
```

or fallback to a single choice:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .singleChoice();
    }
}
```

#### Size

The size of the output image is configurable. However, DALL-E-2 is limited only to square forms.

The possible options for the square size include:

| Method           | Description                                      |
|------------------|--------------------------------------------------|
| `bigSquare()`    | ![1024x1024](https://placehold.co/1024x1024.png) |
| `mediumSquare()` | ![512x512](https://placehold.co/512x512.png)     |
| `smallSquare()`  | ![256x256](https://placehold.co/256x256.png)     |

In this case, an example configuration will look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare();
    }
}
```

#### Format

The format of the output image is configurable between the url and Base 64 options. Please keep in mind that
the url output contains a temporary url and the contained images are deleted after a few hours. If you want to save the
image,
then you have to download it once the response is received.

The format configuration has the following options:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectBase64();
    }
}
```

#### Runtime Selection

Reaching the `andRespond()` method means that the configuration of the response is finished and tou have to choose one
of the runtimes, already explained in details in this document. Basically, the three options look like:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl()
              .andRespond()
              .immediate();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl()
              .andRespond()
              .async();
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl()
              .andRespond()
              .reactive();
    }
}
```

#### Response handling

There are no prompts for image variation generation, so the next stage is to choose the runtime for the HTTP requests.
The output can be either returned as `ImageDataResponse` object or a file downloaded in a location by choice,
respectively:

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl()
              .andRespond()
              .immediate()
              .andGetRaw();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Images.authenticate(FromJson.AUTH)
              .and()
              .another(new File("/var/images/image.png"))
              .withChoices(5)
              .bigSquare()
              .expectUrl()
              .andRespond()
              .immediate()
              .andDownload(new File("/var/target/folder"));
    }
}
```

## Vision API SDK

Vision API gives you the opportunity to get a text explanation of an input image, similar to the reverse prompts
received in the responses of the Images API. You can configure the model used, the tokens used and
also the level of details. The required input is the image that's going to be analyzed by the model.

### Simplified Usage

Use `simply()` to bypass the optional configuration and jump directly to providing an image. Image can be
either a file or Base 64 string.

```java
public class Main {
    public static void main(String[] args) {
        String explanation = Vision.simply()
                                   .explain(new File("/var/tmp/image.png"))
                                   .describe();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        String explanation = Vision.simply()
                                   .explain("ABase64StringHere")
                                   .describe();
    }
}
```

### Model Choosing

The AI model that currently supports Vision API is `GPT40VisionPreviewModel` which is also the default model
configured in the step. There is freedom to choose another model from the existing ones, but keep in mind that
the chances for that to result in an error are big. In the standard case, this step is going to look like that:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision();
    }
}
```

### Tokens Configuration

The Vision API provides flexibility in toggling the amount of tokens that can be used for a request.
You can choose between the `defaultTokens()`, which is going to use the default tokens required by the model,
or `maxTokens()` which basically removes the limitations from the token usage. You can also manually configure the exact
count of the tokens
using the `limitReponseTo(Integer tokens)` method. Generally, lowering the tokens is encouraged only in case
of money saving. In the code, it looks like that:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300);
    }
}
```

### Image for Explanation

Once the tokens have been configured, it's time to provide the mandatory image input that's going to be the object
of the API explanation. The image should be in a file or Base64 format. Keep in mind, the **max size for input image is
20MB**.
If you provide a bigger image, it will result in API error.

Passing the image looks like the following:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"));
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain("StringForBase64FileHere");
    }
}
```

### Image Detail Stage

Once the image has been selected, it's time to choose the level of details observation in the expected response.
Currently, there are only 2 options, values of the `DetailedAnalyze` enumeration, respectively:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH);
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.LOW);
    }
}
```

### Continuous Prompt

If you've already selected one picture and the level of detail, you can either proceed with configuring the runtime
or you can choose another image to be explained. If you want to add more images, you can do that by calling
the `explainAnother()` method and passing the next image as a file or Base 64 string:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("ABase64StringHere");
    }
}
```

### Runtime Selection

If you've added all the needed images, then you can proceed to runtime selection using the `andRespond()` method.
Once you've called it, you can choose among the three possible runtime options:

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("/ABase64StringHere")
              .analyze(DetailedAnalyze.LOW)
              .andRespond()
              .immediate();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("ABase64StringHere")
              .analyze(DetailedAnalyze.LOW)
              .andRespond()
              .async();
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("ABase64StringHere")
              .analyze(DetailedAnalyze.LOW)
              .andRespond()
              .reactive();
    }
}
```

### Prompt

You can optionally provide a prompt for the vision API to clarify your requirements even further.
This can be done by passing the prompt to the `describe()` method. If you don't need any additional
clarifications, you can just call this method to finish the request configuration.

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("/ABase64StringHere")
              .analyze(DetailedAnalyze.LOW)
              .andRespond()
              .immediate()
              .describe("What are the colors in the image");
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Vision.defaults()
              .and()
              .poweredByGpt40Vision()
              .limitResponseTo(300)
              .explain(new File("/var/tmp/image.png"))
              .analyze(DetailedAnalyze.HIGH)
              .explainAnother("/ABase64StringHere")
              .analyze(DetailedAnalyze.LOW)
              .andRespond()
              .immediate()
              .describe();
    }
}
```

## Speech API SDK

Speech API gives you the opportunity to generate audio files, based on a text (so called text-to-speech).
You have (optionally) to configure the AI model, which speaker's voice to use and what should be the
playback speed and output audio format. The only required input is your text that should be converted to speech.

### Simplified Usage

Use `simply()` to bypass the optional configuration and jump directly to providing a text:

```java
public class Main {
    public static void main(String[] args) throws IOException {
        File downloadedAudioFile = Speech.simply()
                                         .dictate("There is a story to tell")
                                         .downloadTo(new File("/var/target/folder"));
    }
}
```

Below you can find how to configure all the optional inputs.

### Model Choosing

Models can be either one of the Text-to-Speech (TTS) models `tts-1` and `tts-1-hd`, provided
by the following fluent methods:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .defaultModel();
    }
}
```

and

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered();
    }
}
```

### Speaker Voice

You can choose the voice of the human which will generated the audio. All supported speakers
are listed in the `Speaker` enumeration. Use it as this:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .voiceOf(Speaker.FABLE);
    }
}
```

or use the default speaker voice:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker();
    }
}
```

### Output Audio Format

You can choose the audio format in which the file to be created with. All supported formats
are listed in the `AudioFormat` enumeration. Use it as this:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker()
              .audio(AudioFormat.FLAC);
    }
}
```

or use some of the fluent methods to choose the format:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker()
              .forStreaming();
    }
}
```

### Playback Speed

You can configure the playback speed by providing a number between 0.2 and 4.0, or
one of the listed values in the `Speed` enumeration, such as:

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker()
              .forStreaming()
              .playback(0.36);
    }
}
```

or

```java
public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker()
              .forStreaming()
              .playback(Speed.HALF_SLOW);
    }
}
```

or use one of the fluent methods:

```java

public class Main {
    public static void main(String[] args) {
        Speech.authenticate(FromJson.AUTH)
              .and()
              .hdPowered()
              .defaultSpeaker()
              .forStreaming()
              .sameSpeed();
    }
}
```

### Generate Audio File

After model, speaker, audio format and speed are configured, you are ready to generate the speech audio file,
based on your prompt. The prompt comes last in configuring the API request params. However, you will need also
to configure where the audio file to be downloaded to:

```java
public class Main {
    public static void main(String[] args) throws IOException {
        File downloadedAudioFile = Speech.authenticate(FromJson.AUTH)
                                         .and()
                                         .hdPowered()
                                         .defaultSpeaker()
                                         .forStreaming()
                                         .sameSpeed()
                                         .immediate()
                                         .dictate("There is a story to tell.")
                                         .downloadTo(new File("/var/target/folder"));
    }
}
```

## Transcription API SDK

Transcription API gives you the opportunity to transcribe audio files.

### Simplified Usage

Use `simply()` to bypass the optional configuration and jump directly to providing an audio file.
Unlike other `simply()` stages, this one allows you to specify the runtime execution (immediate, async or reactive).
For simplicity we are using the blocking one (`immediate()`).

```java
public class Main {
    public static void main(String[] args) {
        String transcription = Transcription.simply()
                                            .transcribe(new File("/var/audio/file.mp3"))
                                            .unguided()
                                            .text();
    }
}
```

### Model Choosing

Models can be only `whisper-1`.

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10();
    }
}
```

### Uploading Audio File

The audio file to be transcribed should be provided as a `File` object, so it should be present in your file system.
In fact, the only rules that apply here, are the rules given by JVM to construct a `File` object. An example usage is:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"));
    }
}
```

### Setting Temperature

You may control the creativity/determinism of the AI Model by providing a `Creativity` instance. Although no other
parameters than temperature (such as `top_p`) are present in this API, most of the theory around this is already
covered in [Chat API SDK / AI Behavior](#AI-Behavior). Use it as follows:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .creativeAs(Creativity.INVENTIVE_AND_UNEXPECTED);
    }
}
```

or by one of the fluent methods:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic();
    }
}
```

### Inputting Language

You can make the AI Model life easier by providing it the language of the input audio. It is highly recommended to
supply it as an `ISO-639-1` format, such as `en`, `de`, `fr`, `ro`, `bg`, `pl`, `zh`, `he` and so on. Usage:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic()
                     .talkingIn("bg");
    }
}
```

or by one of the fluent methods:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic()
                     .talkingInDeutsch();
    }
}
```

### Configuring Response Format

The output usually comes in a DTO containing `text()` getter, or in other words - as a JSON string. It can be
configured so that OpenAI can return plain text or text that is additionally decorated, such as subtitles (`srt`) or
even subtitles with metadata (`vtt`). The SDK will adapt all of them to be returned from the same `text()` getter.

Since everything is adapted the above way, this makes no difference between `text` and `json` response formats.

In order to specify the response format, use one of the fluent methods:

```java
public class Main {
    public static void main(String[] args) {
        Transcription.defaults()
                     .and()
                     .poweredByWhisper10()
                     .transcribe(new File("/var/audio/file.mp3"))
                     .deterministic()
                     .talkingInDeutsch()
                     .subtitles(); // or subtitlesWithMetadata(), or justText()
    }
}
```

Using one of the `subtitles()` or `subtitlesWithMetadata()` formats will generate a text holding the time
markings and the relevant transcription over them.

Example output from `subtitles()` (`.srt`):

```text
1
00:00:00,000 --> 00:00:05,120
Java is the best language in the world. It is so object-oriented that no one understands it

2
00:00:05,120 --> 00:00:09,680
and writes procedural code. People are still coding on the new Java 8.
```

### Additional Prompting

The AI model can be guided additionally what style to use while transcribing or even with prompt
the audio segment can be continued. This step comes after the runtime (async or not) is selected,
and usually is the last step before the request is sent. You can also leave it `unguided()`.
Use `guide(String)` to provide the additional prompt or `unguide()` to leave it as is:

```java
public class Main {
    public static void main(String[] args) {
        String transcript = Transcription.defaults()
                                         .and()
                                         .poweredByWhisper10()
                                         .transcribe(new File("/var/files/java.wav"))
                                         .deterministic()
                                         .talkingInEnglish()
                                         .subtitles()
                                         .immediate()
                                         .guide("It is a rap song.")
                                         .text();

        System.out.println(transcript);
    }
}
```

Which outputs:

```text
1
00:00:00,000 --> 00:00:02,120
Java is the best language in the world.

2
00:00:02,120 --> 00:00:06,880
It is so object-oriented that no one understands it and writes procedural code.

3
00:00:06,880 --> 00:00:09,160
People are still coding on the new Java 8.
```

... well, it seems my audio file was not a rap song at all, but the model still did its best and split
it in some more strophes.

## Translation API SDK

Actually, translations have homogeneous API with transcriptions except the fact you cannot choose a target language.
The idea is that you supply an audio file in language that is not English, and receive back an English translation.

### Example:

```java
public class Main {
    public static void main(String[] args) {
        String translation = Translation.defaults()
                                        .and()
                                        .poweredByWhisper10()
                                        .translate(new File("/var/files/java.wav"))
                                        .randomized()
                                        .subtitles()
                                        .immediate()
                                        .guide("It is a rap song.")
                                        .text();

        System.out.println(translation);
    }
}
```

However, if you want to produce output in different languages from English audio file, you can use the `Transcription`
API. In other words, an example use case can be:

- **[Translations]** Translate Bulgarian audio file to English text
- **[Speech]** Generate English audio from English text
- **[Transcription]** Generate Deutsch transcription from English audio

... or you can just ask the `Chat` SDK to translate the English to German :)

## Contributing

This project is in its very early stage and contributors are very welcomed. If you feel that something has to be
changed or a bug to be fixed, you can report a [new issue](https://github.com/CodexioLtd/openai-api-sdk/issues/new), and
we can take care of it.

If you want to submit directly a code fix, we will be more than glad to see it. Fork the repository and start a clean
branch out of the version you want to patch. When you are finished, make sure all your tests are passing and the
coverage remains in decent level by executing `mvn clean test jacoco:report -Pmvn-deploy`.

Please, use the [code style](https://github.com/CodexioLtd/openai-api-sdk/blob/master/cdx-openai-codestyle.xml)
in the project root folder. If your IDE does not support it, we strongly encourage you just to follow
the code styling in the rest of the classes and methods.

After all your tests are passing and the coverage seems good to you, create a
[pull request](https://github.com/CodexioLtd/openai-api-sdk/compare). We will review the request and either leave
some meaningful suggestions back or maybe merge it and release it with the next release.

The first open beta of the project is 0.8.0 and all the way up to 1.0.0 we will strive to find core
contributors who will serve as project maintainers in the future. [Codexio Ltd.](https://codexio.bg) will remain
the main project maintainer.

...

## License

Copyright 2023 [Codexio Ltd.](https://codexio.bg)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
