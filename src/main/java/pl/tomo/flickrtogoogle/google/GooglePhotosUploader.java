package pl.tomo.flickrtogoogle.google;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;

import java.net.URL;

@Service
@Log
@RequiredArgsConstructor
class GooglePhotosUploader {

    private static final String API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/";

    private final PicasawebService picasawebService;

    @SneakyThrows
    PhotoEntry upload(DownloadedFlickr downloadedFlickr) {

        URL albumPostUrl = new URL(API_PREFIX + "default/albumid/1000000457195984");

        PhotoEntry photoEntry = new PhotoEntry();
        photoEntry.setTitle(new PlainTextConstruct(downloadedFlickr.getTitle().getValue()));
//        photoEntry.setDescription(new PlainTextConstruct(downloadedFlickr.getTitle().getValue()));
//        photoEntry.setClient("myClientName");

        MediaByteArraySource mediaSource = new MediaByteArraySource(downloadedFlickr.getData(), "image/jpeg");

        photoEntry.setMediaSource(mediaSource);

        return picasawebService.insert(albumPostUrl, photoEntry);
    }
}
