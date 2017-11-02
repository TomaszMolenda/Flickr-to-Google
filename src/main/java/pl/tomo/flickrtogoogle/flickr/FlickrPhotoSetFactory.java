package pl.tomo.flickrtogoogle.flickr;

import org.springframework.stereotype.Component;

@Component
class FlickrPhotoSetFactory {

    FlickrPhotoSet create(String flickrId, String name) {

        return new FlickrPhotoSet(flickrId, name);
    }
}
