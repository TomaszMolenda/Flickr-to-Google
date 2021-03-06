package pl.tomo.flickrtogoogle.google;


import com.google.api.client.auth.oauth2.Credential;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrInfoProvider;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrPhotoDownloader;
import pl.tomo.flickrtogoogle.flickr.service.FlickrMediaMarker;

@Service
@RequiredArgsConstructor
@Log
class GooglePhotosCreator {

    private final FlickrInfoProvider flickrInfoProvider;
    private final FlickrPhotoDownloader flickrPhotoDownloader;
    private final GooglePhotosUploader googlePhotosUploader;
    private final Credential credential;
    private final FlickrMediaMarker flickrMediaMarker;

    @Scheduled(fixedRate = 1000)
    private void create() {

        flickrInfoProvider.fetchPhotosIds().stream()
                .limit(100)
                .map(flickrPhotoDownloader::download)
                .filter(DownloadedFlickr::isPhoto)
                .forEach(this::upload);
    }

    private void upload(DownloadedFlickr downloadedFlickr) {

        try {

            googlePhotosUploader.upload(downloadedFlickr);

            flickrMediaMarker.markUploaded(downloadedFlickr.getFlickrId());

            return;

        } catch (Exception e) {

            if (e.getMessage().equals("Forbidden")) {

                refreshToken();

                upload(downloadedFlickr);
            }
        }

        throw new RuntimeException();
    }

    @SneakyThrows
    private void refreshToken() {

        credential.refreshToken();
    }
}
