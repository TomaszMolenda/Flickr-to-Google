package pl.tomo.flickrtogoogle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.tomo.flickrtogoogle.google.GooglePhotosUploader;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final GooglePhotosUploader googlePhotosUploader;
    private final PhotoRepository photoRepository;
    private final Credential credential;


    public void reportCurrentTime() {



        byte[] flickrPhotos = new byte[]{1};

        log.info("Photos in database: {}", photoRepository.count());

        try {

            PhotoEntry photoEntry = googlePhotosUploader.upload(flickrPhotos);
        } catch (Exception e) {

            if (e.getMessage().equals("Forbidden")) {

                try {
                    credential.refreshToken();
                    googlePhotosUploader.upload(flickrPhotos);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }

//        Photo photo = new Photo(photoEntry.getId(), photoEntry.getTitle().getPlainText());
//
//        photoRepository.save(photo);
//
//        log.info("Photo id: {}", photoEntry.getId());
    }
}