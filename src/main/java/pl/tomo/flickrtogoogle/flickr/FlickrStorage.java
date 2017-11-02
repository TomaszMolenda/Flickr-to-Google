package pl.tomo.flickrtogoogle.flickr;

import com.flickr4java.flickr.photosets.Photoset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class FlickrStorage {

    private final FlickrPhotoSetFactory flickrPhotoSetFactory;
    private final FlickrPhotoSetRepository flickrPhotoSetRepository;

    @Autowired
    FlickrStorage(FlickrPhotoSetFactory flickrPhotoSetFactory, FlickrPhotoSetRepository flickrPhotoSetRepository) {
        this.flickrPhotoSetFactory = flickrPhotoSetFactory;
        this.flickrPhotoSetRepository = flickrPhotoSetRepository;
    }

    void savePhotoSet(Photoset photoSet) {

        final String flickrId = photoSet.getId();
        final String name = photoSet.getTitle();

        FlickrPhotoSet flickrPhotoSet = flickrPhotoSetFactory.create(flickrId, name);

        if (notExist(flickrPhotoSet)) {

            flickrPhotoSetRepository.save(flickrPhotoSet);
        }
    }

    private boolean notExist(FlickrPhotoSet flickrPhotoSet) {

        FlickrPhotoSet firstByFlickId = flickrPhotoSetRepository.findFirstByFlickId(flickrPhotoSet.getFlickId());

        return firstByFlickId == null;
    }
}
