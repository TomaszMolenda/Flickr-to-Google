package pl.tomo.flickrtogoogle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhotoCreator;
import pl.tomo.flickrtogoogle.flickr.set.FlickrPhotoSetCreator;
import pl.tomo.flickrtogoogle.google.GooglePhotosUploader;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final GooglePhotosUploader googlePhotosUploader;
    private final PhotoRepository photoRepository;
    private final FlickrPhotoSetCreator flickrPhotoSetCreator;
    private final FlickrPhotoCreator flickrPhotoCreator;
    private final Credential credential;

    @Autowired
    public ScheduledTasks(GooglePhotosUploader googlePhotosUploader, PhotoRepository photoRepository, FlickrPhotoSetCreator flickrPhotoSetCreator, FlickrPhotoCreator flickrPhotoCreator, Credential credential) {
        this.googlePhotosUploader = googlePhotosUploader;
        this.photoRepository = photoRepository;
        this.flickrPhotoSetCreator = flickrPhotoSetCreator;
        this.flickrPhotoCreator = flickrPhotoCreator;
        this.credential = credential;
    }

    @Scheduled(fixedRate = 50000000)
    public void reportCurrentTime() {

        flickrPhotoSetCreator.create();
        flickrPhotoCreator.create();

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