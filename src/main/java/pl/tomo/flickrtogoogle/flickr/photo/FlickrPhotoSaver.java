package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
@RequiredArgsConstructor
class FlickrPhotoSaver {

    private final FlickrPhotoFactory flickrPhotoFactory;
    private final FlickrPhotoExistChecker flickrPhotoExistChecker;
    private final FlickrPhotoRepository flickrPhotoRepository;

    void save(List<Photo> photos) {

        for (Photo photo : photos) {

            final FlickrPhoto flickrPhoto = flickrPhotoFactory.create(photo);

            if (flickrPhotoExistChecker.notExist(flickrPhoto)) {

                flickrPhotoRepository.save(flickrPhoto);

                log.info("FlickrPhoto saved, id: " + flickrPhoto.getFlickrId().getId());
            }
        }
    }
}
