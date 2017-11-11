package pl.tomo.flickrtogoogle.flickr.set;

import com.flickr4java.flickr.photosets.Photoset;
import org.springframework.stereotype.Component;

@Component
class FlickrPhotoSetFactory {

    FlickrPhotoSet create(Photoset photoSet) {

        final String flickrId = photoSet.getId();
        final String name = photoSet.getTitle();
        final int photoCount = photoSet.getPhotoCount();
        final int videoCount = photoSet.getVideoCount();

        return new FlickrPhotoSet(flickrId, name, photoCount, videoCount);
    }
}
