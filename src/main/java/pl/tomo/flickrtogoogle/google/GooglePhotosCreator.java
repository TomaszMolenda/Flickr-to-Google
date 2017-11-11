package pl.tomo.flickrtogoogle.google;


import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrInfoProvider;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrPhotoDownloader;

@Service
@RequiredArgsConstructor
@Log
class GooglePhotosCreator {

    private final FlickrInfoProvider flickrInfoProvider;
    private final FlickrPhotoDownloader flickrPhotoDownloader;
    private final GooglePhotosUploader googlePhotosUploader;
    private final Credential credential;

    @Scheduled(fixedRate = 50000000)
    private void create() {

        flickrInfoProvider.fetchPhotosIds().stream()
                .limit(100)
                .map(flickrPhotoDownloader::download)
                .forEach(this::upload);
    }

    private PhotoEntry upload(DownloadedFlickr downloadedFlickr) {

        try {

            final PhotoEntry photoEntry = googlePhotosUploader.upload(downloadedFlickr);

            log.info("Successfully upload photo to google " + photoEntry.getId());

            return photoEntry;

        } catch (Exception e) {

            if (e.getMessage().equals("Forbidden")) {

                refreshToken();

                return upload(downloadedFlickr);
            }
        }

        throw new RuntimeException();
    }

    @SneakyThrows
    private void refreshToken() {

        credential.refreshToken();
    }
}
