package pl.tomo.flickrtogoogle.flickr.set;

import lombok.Getter;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhoto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"flickrId", "name"}))
@Getter
public class FlickrPhotoSet {

    static final int PHOTOS_PER_PAGE = 100;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String flickrId;

    private String name;

    private Integer photoCount;

    private Integer videoCount;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<FlickrPhoto> flickrPhotos;


    private FlickrPhotoSet() {}

    FlickrPhotoSet(String flickrId, String name, int photoCount, int videoCount) {
        this.flickrId = flickrId;
        this.name = name;
        this.photoCount = photoCount;
        this.videoCount = videoCount;
        this.flickrPhotos = new HashSet<>();
    }

    public void addPhoto(FlickrPhoto flickrPhoto) {

        flickrPhotos.add(flickrPhoto);
    }
}
