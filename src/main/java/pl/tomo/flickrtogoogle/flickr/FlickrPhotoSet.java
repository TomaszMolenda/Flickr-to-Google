package pl.tomo.flickrtogoogle.flickr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class FlickrPhotoSet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String flickId;
    private String name;

    private FlickrPhotoSet() {
    }

    public FlickrPhotoSet(String flickId, String name) {
        this.flickId = flickId;
        this.name = name;
    }
}
