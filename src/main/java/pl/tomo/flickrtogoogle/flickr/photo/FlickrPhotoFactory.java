package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.photos.Photo;
import org.springframework.stereotype.Component;
import pl.tomo.flickrtogoogle.flickr.FlickrId;

import java.util.Date;

@Component
class FlickrPhotoFactory {

    FlickrPhoto create(Photo photo) {

        final FlickrId flickrId = new FlickrId(photo.getId());
        final Date dateTaken = photo.getDateTaken();
        final String title = photo.getTitle();

        return new FlickrPhoto(flickrId, dateTaken, title);
    }
}
