package pl.tomo.flickrtogoogle.flickr.ports.outgoing;

import lombok.RequiredArgsConstructor;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.Title;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.DownloadedFlickr;
import pl.tomo.flickrtogoogle.flickr.adapters.outgoing.MediaType;

@RequiredArgsConstructor
class FlickrDownloadedData implements DownloadedFlickr {

    private final FlickrId flickrId;
    private final Title title;
    private final byte[] data;
    private final MediaType mediaType;


    @Override
    public FlickrId getFlickrId() {
        return flickrId;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public boolean isPhoto() {
        return mediaType == MediaType.PHOTO;
    }
}
