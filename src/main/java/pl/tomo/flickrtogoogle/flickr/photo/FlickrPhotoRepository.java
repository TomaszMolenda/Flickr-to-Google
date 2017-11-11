package pl.tomo.flickrtogoogle.flickr.photo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlickrPhotoRepository extends CrudRepository<FlickrPhoto, Long> {

    FlickrPhoto findFirstByFlickrId(String flickrId);
}
