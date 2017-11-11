package pl.tomo.flickrtogoogle.flickr.set;

import org.springframework.stereotype.Component;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhoto;
import pl.tomo.flickrtogoogle.flickr.set.dto.FlickrPhotoSetDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
class FlickrPhotoSetDtoFactory {

    FlickrPhotoSetDto create(FlickrPhotoSet flickrPhotoSet) {

        final String flickrId = flickrPhotoSet.getFlickrId();
        final Integer mediaCount = flickrPhotoSet.getPhotoCount() + flickrPhotoSet.getVideoCount();
        final List<String> flickrPhotosIds = flickrPhotoSet.getFlickrPhotos()
                .stream()
                .map(FlickrPhoto::getFlickrId)
                .collect(Collectors.toList());

        return new FlickrPhotoSetDto(flickrId, mediaCount, flickrPhotosIds);
    }
}
