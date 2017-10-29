package pl.tomo.flickrtogoogle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.common.collect.Lists;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.Version;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;

@Service
public class Test2 implements InitializingBean {

    private static final String API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/";

    @Override
    public void afterPropertiesSet() throws Exception {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();


        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jacksonFactory,
                new InputStreamReader(new FileInputStream("/home/tomo/client_secret.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jacksonFactory, clientSecrets,
                Collections.singleton("https://picasaweb.google.com/data/")).setDataStoreFactory(new FileDataStoreFactory(new File("/home/tomo/accessToken")))
                .setApprovalPrompt("force")
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        final PicasawebService service = new PicasawebService("tomo");
        service.setOAuth2Credentials(credential);

        URL albumPostUrl = new URL(API_PREFIX + "default/albumid/1000000457195984");

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct("Puppies FTW"));
        myPhoto.setDescription(new PlainTextConstruct("Puppies are the greatest."));
        myPhoto.setClient("myClientName");
        myPhoto.setFeaturedDate(Date.from(LocalDate.parse("2017-01-01", DateTimeFormatter.ISO_DATE).atStartOfDay().toInstant(ZoneOffset.UTC)));

        MediaFileSource myMedia = new MediaFileSource(new File("/home/tomo/kot2.jpeg"), "image/jpeg");
        myPhoto.setMediaSource(myMedia);

        PhotoEntry returnedPhoto = service.insert(albumPostUrl, myPhoto);

        System.out.println(returnedPhoto.getId());
    }
}
