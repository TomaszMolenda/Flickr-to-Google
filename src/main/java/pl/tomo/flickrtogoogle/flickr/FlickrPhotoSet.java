package pl.tomo.flickrtogoogle.flickr;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"flickId", "name"}))
@Getter
public class FlickrPhotoSet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String flickId;

    private String name;

    private FlickrPhotoSet() {}

    FlickrPhotoSet(String flickId, String name) {
        this.flickId = flickId;
        this.name = name;
    }
}
