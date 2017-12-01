package pl.tomo.flickrtogoogle.flickr.photo;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.MediaType;

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

    private MediaType mediaType;

    private String problem;

    private Boolean uploaded;

    FlickrPhoto() {
    }

    FlickrPhoto(FlickrId flickrId, Date dateTaken, String title) {

        this.flickrId = flickrId;
        this.dateTaken = dateTaken;
        this.title = title;
    }

    public void markVideo() {
        mediaType = MediaType.VIDEO;
    }

    public void markProblem(String problem) {

        this.problem = problem;
    }

    public void markUploaded() {

        uploaded = true;
    }

    public boolean nonUploaded() {

        if (uploaded == null) {

            return true;
        }

        return !uploaded;
    }

    public boolean isNotVideo() {

        return mediaType != MediaType.VIDEO;
    }

    public boolean doesNotHaveProblem() {

        return StringUtils.isEmpty(problem);
    }
}
