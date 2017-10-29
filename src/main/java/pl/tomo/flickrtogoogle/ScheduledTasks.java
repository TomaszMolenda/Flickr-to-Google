package pl.tomo.flickrtogoogle;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gdata.data.photos.PhotoEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final GooglePhotosUploader googlePhotosUploader;
    private final PhotoRepository photoRepository;

    @Autowired
    public ScheduledTasks(GooglePhotosUploader googlePhotosUploader, PhotoRepository photoRepository) {
        this.googlePhotosUploader = googlePhotosUploader;
        this.photoRepository = photoRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        log.info("Photos in database: {}", photoRepository.count());

        PhotoEntry photoEntry = googlePhotosUploader.upload();

        Photo photo = new Photo(photoEntry.getId(), photoEntry.getTitle().getPlainText());

        photoRepository.save(photo);

        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}