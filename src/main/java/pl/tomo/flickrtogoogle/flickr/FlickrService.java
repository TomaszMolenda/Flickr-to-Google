package pl.tomo.flickrtogoogle.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.auth.Auth;
import lombok.Getter;

@Getter
public class FlickrService {

    private final Flickr flickr;
    private final Auth auth;

    FlickrService(Flickr flickr, Auth auth) {
        this.flickr = flickr;
        this.auth = auth;
    }
}
