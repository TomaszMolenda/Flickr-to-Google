package pl.tomo.flickrtogoogle;

import com.google.api.client.auth.oauth2.Credential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {


    private final PhotoRepository photoRepository;
    private final Credential credential;


    public void reportCurrentTime() {



//        byte[] flickrPhotos = new byte[]{1};
//
//        log.info("Photos in database: {}", photoRepository.count());
//
//        try {
//
//            PhotoEntry photoEntry = googlePhotosUploader.upload(flickrPhotos);
//        } catch (Exception e) {
//
//            if (e.getMessage().equals("Forbidden")) {
//
//                try {
//                    credential.refreshToken();
//                    googlePhotosUploader.upload(flickrPhotos);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//        }

//        Photo photo = new Photo(photoEntry.getId(), photoEntry.getTitle().getPlainText());
//
//        photoRepository.save(photo);
//
//        log.info("Photo id: {}", photoEntry.getId());
    }
}