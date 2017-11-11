package pl.tomo.flickrtogoogle.flickr.photo;

import lombok.Getter;
import pl.tomo.flickrtogoogle.flickr.FlickrId;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FlickrPhoto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Getter
    @Embedded
    private FlickrId flickrId;

    private Date dateTaken;

    @Getter
    private String title;

    FlickrPhoto() {
    }

    FlickrPhoto(FlickrId flickrId, Date dateTaken, String title) {

        this.flickrId = flickrId;
        this.dateTaken = dateTaken;
        this.title = title;
    }
}
