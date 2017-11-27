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
public class FlickrMediaMarker {

    private final FlickrPhotoRepository flickrPhotoRepository;

    public void markVideo(FlickrId flickrId) {

        final FlickrPhoto flickrPhoto = obtainFlickrPhoto(flickrId);

        flickrPhoto.markVideo();

        save(flickrPhoto);

        log.warning(String.format("Flick media set as video, id: %s", flickrId.getId()));
    }

    public void markNonDate(FlickrId flickrId) {

        final FlickrPhoto flickrPhoto = obtainFlickrPhoto(flickrId);

        flickrPhoto.markProblem("Non date");

        save(flickrPhoto);

        log.warning(String.format("Flick media set photo as problem, id: %s", flickrId.getId()));
    }

    public void markUploaded(FlickrId flickrId) {

        final FlickrPhoto flickrPhoto = obtainFlickrPhoto(flickrId);

        flickrPhoto.markUploaded();

        save(flickrPhoto);

        log.info(String.format("Successfully uploaded photo to google, id: %s", flickrId.getId()));


    }

    private FlickrPhoto obtainFlickrPhoto(FlickrId flickrId) {
        return flickrPhotoRepository.findFirstByFlickrId(flickrId);
    }

    private FlickrPhoto save(FlickrPhoto flickrPhoto) {
        return flickrPhotoRepository.save(flickrPhoto);
    }
}
