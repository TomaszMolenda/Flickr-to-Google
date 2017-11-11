package pl.tomo.flickrtogoogle.flickr.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FlickrPhotoSetExistChecker {

    private final FlickrPhotoSetRepository flickrPhotoSetRepository;

    @Autowired
    FlickrPhotoSetExistChecker(FlickrPhotoSetRepository flickrPhotoSetRepository) {
        this.flickrPhotoSetRepository = flickrPhotoSetRepository;
    }

    boolean notExist(FlickrPhotoSet flickrPhotoSet) {

        return flickrPhotoSetRepository.findFirstByFlickrId(flickrPhotoSet.getFlickrId()) == null;
    }
}
