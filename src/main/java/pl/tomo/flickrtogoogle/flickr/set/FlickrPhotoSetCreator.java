package pl.tomo.flickrtogoogle.flickr.set;

import com.flickr4java.flickr.Flickr;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.service.FlickrService;
import pl.tomo.flickrtogoogle.flickr.service.FlickrServiceCreator;

@Service
public class FlickrPhotoSetCreator {

    private final FlickrServiceCreator flickrServiceCreator;
    private final FlickrPhotoSetFactory flickrPhotoSetFactory;
    private final FlickrPhotoSetExistChecker flickrPhotoSetExistChecker;
    private final FlickrPhotoSetRepository flickrPhotoSetRepository;

    FlickrPhotoSetCreator(FlickrServiceCreator flickrServiceCreator, FlickrPhotoSetFactory flickrPhotoSetFactory, FlickrPhotoSetExistChecker flickrPhotoSetExistChecker, FlickrPhotoSetRepository flickrPhotoSetRepository) {
        this.flickrServiceCreator = flickrServiceCreator;
        this.flickrPhotoSetFactory = flickrPhotoSetFactory;
        this.flickrPhotoSetExistChecker = flickrPhotoSetExistChecker;
        this.flickrPhotoSetRepository = flickrPhotoSetRepository;
    }

    @SneakyThrows
    public void create() {

        final FlickrService flickrService = flickrServiceCreator.create();

        final String userId = flickrService.obtainUserId();
        final Flickr flickr = flickrService.getFlickr();

        flickr.getPhotosetsInterface()
                .getList(userId)
                .getPhotosets()
                .stream()
                .map(flickrPhotoSetFactory::create)
                .filter(flickrPhotoSetExistChecker::notExist)
                .forEach(flickrPhotoSetRepository::save);
    }
}
