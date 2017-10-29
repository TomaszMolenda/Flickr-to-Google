package pl.tomo.flickrtogoogle;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gdata.data.photos.PhotoEntry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomo.flickrtogoogle.flickr.FlickrUploader;
import pl.tomo.flickrtogoogle.google.GooglePhotosUploader;

@Slf4j
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final GooglePhotosUploader googlePhotosUploader;
    private final PhotoRepository photoRepository;
    private final FlickrUploader flickrUploader;

    @Autowired
    public ScheduledTasks(GooglePhotosUploader googlePhotosUploader, PhotoRepository photoRepository, FlickrUploader flickrUploader) {
        this.googlePhotosUploader = googlePhotosUploader;
        this.photoRepository = photoRepository;
        this.flickrUploader = flickrUploader;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        byte[] flickrPhotos = flickrUploader.upload();

        log.info("Photos in database: {}", photoRepository.count());

        PhotoEntry photoEntry = googlePhotosUploader.upload(flickrPhotos);

        Photo photo = new Photo(photoEntry.getId(), photoEntry.getTitle().getPlainText());

        photoRepository.save(photo);

        log.info("Photo id: {}", photoEntry.getId());
    }
}