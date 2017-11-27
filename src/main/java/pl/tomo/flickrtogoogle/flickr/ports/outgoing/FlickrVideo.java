package pl.tomo.flickrtogoogle.flickr.ports.outgoing;

import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.MediaType;

class FlickrVideo extends FlickrDownloadedData {

    FlickrVideo() {

        super(null, null,null, MediaType.VIDEO);
    }
}
