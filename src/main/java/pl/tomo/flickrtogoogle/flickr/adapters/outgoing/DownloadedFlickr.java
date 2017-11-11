package pl.tomo.flickrtogoogle.flickr.adapters.outgoing;

import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.Title;

public interface DownloadedFlickr {

    FlickrId getFlickrId();
    Title getTitle();
    byte[] getData();
}
