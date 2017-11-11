package pl.tomo.flickrtogoogle.flickr.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.auth.Auth;
import lombok.Getter;


public class FlickrService {

    @Getter
    private final Flickr flickr;
    private final Auth auth;

    FlickrService(Flickr flickr, Auth auth) {
        this.flickr = flickr;
        this.auth = auth;
    }

    public String obtainUserId() {

        return auth.getUser().getId();
    }
}
