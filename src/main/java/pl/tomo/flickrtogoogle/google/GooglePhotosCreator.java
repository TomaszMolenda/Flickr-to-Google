package pl.tomo.flickrtogoogle.google;


import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrInfoProvider;
import pl.tomo.flickrtogoogle.flickr.ports.outgoing.FlickrPhotoDownloader;

import java.util.List;

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

        List<FlickrId> flickrPhotosIds = flickrInfoProvider.fetchPhotosIds();

        final DownloadedFlickr downloadedFlickr = flickrPhotoDownloader.download(flickrPhotosIds.get(0));

        PhotoEntry photoEntry = upload(downloadedFlickr);

        log.info("Successfully upload photo to google " + photoEntry.getId());
    }

    private PhotoEntry upload(DownloadedFlickr downloadedFlickr) {

        try {

            return googlePhotosUploader.upload(downloadedFlickr);

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
