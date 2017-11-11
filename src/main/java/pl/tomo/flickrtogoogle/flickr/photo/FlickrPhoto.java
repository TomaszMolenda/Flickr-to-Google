package pl.tomo.flickrtogoogle.flickr.photo;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class FlickrPhoto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Getter
    private String flickrId;

    private Date dateTaken;

    private String title;

    FlickrPhoto() {
    }

    FlickrPhoto(String flickrId, Date dateTaken, String title) {

        this.flickrId = flickrId;
        this.dateTaken = dateTaken;
        this.title = title;
    }
}
