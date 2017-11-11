package pl.tomo.flickrtogoogle.flickr.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FlickrPhotoExistChecker {

    private final FlickrPhotoRepository flickrPhotoRepository;

    @Autowired
    FlickrPhotoExistChecker(FlickrPhotoRepository flickrPhotoRepository) {
        this.flickrPhotoRepository = flickrPhotoRepository;
    }

    boolean notExist(FlickrPhoto flickrPhoto) {

        return flickrPhotoRepository.findFirstByFlickrId(flickrPhoto.getFlickrId()) == null;
    }
}
