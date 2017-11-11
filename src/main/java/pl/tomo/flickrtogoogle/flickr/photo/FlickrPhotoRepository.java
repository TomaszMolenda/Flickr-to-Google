package pl.tomo.flickrtogoogle.flickr.photo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tomo.flickrtogoogle.flickr.FlickrId;

@Repository
public interface FlickrPhotoRepository extends CrudRepository<FlickrPhoto, Long> {

    FlickrPhoto findFirstByFlickrId(FlickrId flickrId);
}
