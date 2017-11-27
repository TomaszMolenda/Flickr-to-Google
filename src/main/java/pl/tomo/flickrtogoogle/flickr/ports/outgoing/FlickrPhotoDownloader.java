package pl.tomo.flickrtogoogle.flickr.ports.outgoing;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
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
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.MediaType;
import pl.tomo.flickrtogoogle.flickr.service.FlickrMediaMarker;
import pl.tomo.flickrtogoogle.flickr.service.FlickrServiceCreator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Log
public class FlickrPhotoDownloader {

    private final FlickrServiceCreator flickrServiceCreator;
    private final FlickrMediaMarker flickrMediaMarker;

    @SneakyThrows
    public DownloadedFlickr download(FlickrId flickrId) {

        final PhotosInterface photosInterface = flickrServiceCreator.create()
                .getFlickr()
                .getPhotosInterface();

        final Photo photo = photosInterface.getPhoto(flickrId.getId());

        return downloadMediaData(photo, flickrId);
    }

    @SneakyThrows
    private DownloadedFlickr downloadMediaData(Photo photo, FlickrId flickrId) {

        final PhotosInterface photosInterface = flickrServiceCreator.create().getFlickr().getPhotosInterface();

        if (isVideo(photo)) {

            flickrMediaMarker.markVideo(flickrId);

            return new FlickrVideo();

        } else {

            final byte[] bytes = IOUtils.toByteArray(photosInterface.getImageAsStream(photo, Size.ORIGINAL));

            final InputStream inputStream = new ByteArrayInputStream(bytes);
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            final Metadata metadata = ImageMetadataReader.readMetadata(bufferedInputStream);

            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            if (directory == null) {

                flickrMediaMarker.markNonDate(flickrId);

                return new NonDateFlickrPhoto();
            }

            log.info(String.format("Download photo from flickr, title: %s, id: %s", photo.getTitle(), photo.getId()));

            return new FlickrPhoto(flickrId, new Title(photo.getTitle()), bytes, MediaType.PHOTO);
        }
    }

    private boolean isVideo(Photo photo) {

        return photo.getMedia().equals("video");
    }
}
