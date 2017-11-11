package pl.tomo.flickrtogoogle.flickr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FlickrId {

    @Column(name = "flickrId")
    @Getter
    private String id;
}
