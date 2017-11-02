package pl.tomo.flickrtogoogle.flickr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlickrPhotoSetRepository extends CrudRepository<FlickrPhotoSet, Long> {

    FlickrPhotoSet findFirstByFlickId(String flickrId);
}
