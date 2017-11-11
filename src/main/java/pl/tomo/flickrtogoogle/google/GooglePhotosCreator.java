package pl.tomo.flickrtogoogle.google;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.service.FlickrIdProvider;
import pl.tomo.flickrtogoogle.flickr.service.FlickrPhotoDownloader;

import java.util.List;

@Service
@RequiredArgsConstructor
class GooglePhotosCreator {

    private final FlickrIdProvider flickrIdProvider;
    private final FlickrPhotoDownloader flickrPhotoDownloader;


    @Scheduled(fixedRate = 50000000)
    private void create() {

        List<FlickrId> flickrPhotosIds = flickrIdProvider.fetchPhotosIds();

        flickrPhotoDownloader.download("dsds");


    }
}
