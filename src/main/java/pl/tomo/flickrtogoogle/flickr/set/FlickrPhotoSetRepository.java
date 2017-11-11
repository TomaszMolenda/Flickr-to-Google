package pl.tomo.flickrtogoogle.flickr.set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlickrPhotoSetRepository extends CrudRepository<FlickrPhotoSet, Long> {

    FlickrPhotoSet findFirstByFlickrId(String flickrId);
    List<FlickrPhotoSet> findAll();
}
