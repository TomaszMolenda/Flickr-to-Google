package pl.tomo.flickrtogoogle.flickr.ports.outgoing;

import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.Title;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.MediaType;

class FlickrPhoto extends FlickrDownloadedData {

    FlickrPhoto(FlickrId flickrId, Title title, byte[] data, MediaType mediaType) {
        super(flickrId, title, data, mediaType);
    }
}
