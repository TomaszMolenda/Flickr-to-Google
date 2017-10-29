package pl.tomo.flickrtogoogle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.photos.PicasawebService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;

@Configuration
class GoogleConfiguration {

    @Bean
    @SneakyThrows
    HttpTransport httpTransport() {

        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    JacksonFactory jacksonFactory() {

        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    @SneakyThrows
    GoogleClientSecrets googleClientSecrets(JacksonFactory jacksonFactory) {

        return GoogleClientSecrets.load(jacksonFactory,
                new InputStreamReader(new FileInputStream("/home/tomo/client_secret.json")));
    }

    @Bean
    @SneakyThrows
    GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow(HttpTransport httpTransport,
                                                                    JacksonFactory jacksonFactory,
                                                                    GoogleClientSecrets googleClientSecrets) {

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jacksonFactory, googleClientSecrets,
                Collections.singleton("https://picasaweb.google.com/data/"))
                .setDataStoreFactory(new FileDataStoreFactory(new File("/home/tomo/accessToken")))
                .setApprovalPrompt("force")
                .setAccessType("offline")
                .build();
    }

    @Bean
    @SneakyThrows
    Credential credential(GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow) {

        return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, new LocalServerReceiver()).authorize("user");
    }

    @Bean
    PicasawebService picasawebService(Credential credential) {

        PicasawebService picasawebService = new PicasawebService("picasawebService");

        picasawebService.setOAuth2Credentials(credential);

        return picasawebService;
    }



}
