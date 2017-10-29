package pl.tomo.flickrtogoogle.google;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.PhotoEntry;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
@Log
public class GooglePhotosUploader {

    private static final String API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/";

    private final PicasawebService picasawebService;

    @Autowired
    GooglePhotosUploader(PicasawebService picasawebService) {
        this.picasawebService = picasawebService;
    }

    @SneakyThrows
    public PhotoEntry upload() {

        URL albumPostUrl = new URL(API_PREFIX + "default/albumid/1000000457195984");

        PhotoEntry photoEntry = new PhotoEntry();
        photoEntry.setTitle(new PlainTextConstruct("Puppies FTW"));
        photoEntry.setDescription(new PlainTextConstruct("Puppies are the greatest."));
        photoEntry.setClient("myClientName");

        MediaFileSource mediaFileSource = new MediaFileSource(new File("/home/tomo/kot2.jpeg"), "image/jpeg");
        photoEntry.setMediaSource(mediaFileSource);

        return picasawebService.insert(albumPostUrl, photoEntry);
    }
}
