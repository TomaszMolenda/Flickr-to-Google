package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.service.FlickrService;
import pl.tomo.flickrtogoogle.flickr.service.FlickrServiceCreator;

import java.util.List;

@Service
@Log
@RequiredArgsConstructor
class FlickrPhotoCreator {

    private static final int PER_PAGE = 100;

    private final FlickrServiceCreator flickrServiceCreator;
    private final FlickrPhotoSaver flickrPhotoSaver;

    @Scheduled(fixedRate = 50000000)
    private void create() {

        final FlickrService flickrService = flickrServiceCreator.create();

        int downloaded;
        int page = 1;

        do {

            final List<Photo> photos = download(flickrService, page);

            downloaded = photos.size();
            log.info("Download photos " + photos.size());

            flickrPhotoSaver.save(photos);

            page++;

        } while (downloaded == PER_PAGE);
    }

    @SneakyThrows
    private List<Photo> download(FlickrService flickrService, int page) {

        log.info("Downloading photos from page " + page);

        AllPhotosInterface allPhotosInterface = new AllPhotosInterface(flickrService);

        return allPhotosInterface.getAll(PER_PAGE, page);
    }
}
