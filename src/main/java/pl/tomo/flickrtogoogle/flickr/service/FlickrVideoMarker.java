package pl.tomo.flickrtogoogle.flickr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhoto;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhotoRepository;

@Service
@RequiredArgsConstructor
@Log
public class FlickrVideoMarker {

    private final FlickrPhotoRepository flickrPhotoRepository;

    public void mark(FlickrId flickrId) {

        final FlickrPhoto flickrPhoto = flickrPhotoRepository.findFirstByFlickrId(flickrId);

        flickrPhoto.markVideo();

        flickrPhotoRepository.save(flickrPhoto);

        log.info(String.format("Flick media set as video, id: %s", flickrId.getId()));
    }
}
