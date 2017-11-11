package pl.tomo.flickrtogoogle.flickr.ports.outgoing;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.Size;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.Title;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;
import pl.tomo.flickrtogoogle.flickr.service.FlickrServiceCreator;

@Service
@RequiredArgsConstructor
@Log
public class FlickrPhotoDownloader {

    private final FlickrServiceCreator flickrServiceCreator;

    @SneakyThrows
    public DownloadedFlickr download(FlickrId flickrId) {

        final PhotosInterface photosInterface = flickrServiceCreator.create()
                .getFlickr()
                .getPhotosInterface();

        final Photo photo = photosInterface.getPhoto(flickrId.getId());

        log.info("Download original photo with id" + photo.getId());

        final byte[] bytes = IOUtils.toByteArray(photosInterface.getImageAsStream(photo, Size.ORIGINAL));

        return new DownloadedFlickr() {

            @Override
            public FlickrId getFlickrId() {

                return flickrId;
            }

            @Override
            public Title getTitle() {

                return new Title(photo.getTitle());
            }

            @Override
            public byte[] getData() {

                return bytes;
            }
        };
    }
}
