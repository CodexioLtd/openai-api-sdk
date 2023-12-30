package bg.codexio.ai.openai.api.examples.chat;

import bg.codexio.ai.openai.api.http.AuthenticationInterceptor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.chat.Chat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.OkHttpClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatAskWithCustomOkHttpClientObjectMapper {

    public static void main(String[] args) {
        var console = new Scanner(System.in);

        System.out.print("OpenAI API Key: ");
        var apiKey = console.nextLine();

        System.out.print("OpenAI Organization (Enter to skip): ");
        var organization = console.nextLine()
                                  .trim();

        System.out.print("OpenAI API Base URL (Enter to use default): ");
        var inputBaseUrl = console.nextLine();
        var baseUrl = inputBaseUrl.isBlank()
                      ? ApiCredentials.BASE_URL
                      : inputBaseUrl;

        var ctx = new HttpExecutorContext(new ApiCredentials(
                apiKey,
                organization,
                baseUrl
        ));

        System.out.println(ctx.credentials());

        var client =
                new OkHttpClient.Builder().addInterceptor(new AuthenticationInterceptor(ctx))
                                               .sslSocketFactory(
                                                       HttpsURLConnection.getDefaultSSLSocketFactory(),
                                                       new TrustAllX509TrustManager()
                                               )
                                               .hostnameVerifier((
                                                                         hostname, session
                                                                 ) -> true)
                                               .connectionPool(new okhttp3.ConnectionPool(
                                                       5,
                                                       10,
                                                       TimeUnit.MINUTES
                                               ))
                                               .build();

        // ObjectMapper with additional configurations
        // Don't recommend to change the transformation of the cases though
        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        var executor = new ChatHttpExecutor(
                client,
                baseUrl,
                mapper
        );

        var response = Chat.throughHttp(executor)
                           .poweredByGPT40()
                           .predictable()
                           .andRespond()
                           .immediate()
                           .ask("Is coffee the coolest drink on the planet?");

        System.out.println(response);
    }
}

/**
 * Custom and pretty unsafe trust manager for demo purposes
 */
class TrustAllX509TrustManager
        implements X509TrustManager {

    @Override
    public void checkClientTrusted(
            X509Certificate[] x509Certificates,
            String s
    ) {
        // Accept all client certificates
    }

    @Override
    public void checkServerTrusted(
            X509Certificate[] x509Certificates,
            String s
    ) {
        // Accept all server certificates
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
