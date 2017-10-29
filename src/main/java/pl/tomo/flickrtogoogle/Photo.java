package pl.tomo.flickrtogoogle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String googleId;
    private String title;

    private Photo() {

    }

    public Photo(String googleId, String title) {
        this.googleId = googleId;
        this.title = title;
    }
}
