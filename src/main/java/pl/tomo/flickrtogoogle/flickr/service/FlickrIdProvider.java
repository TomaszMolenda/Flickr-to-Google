package pl.tomo.flickrtogoogle.flickr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.FlickrId;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhoto;
import pl.tomo.flickrtogoogle.flickr.photo.FlickrPhotoRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FlickrIdProvider {

    private final FlickrPhotoRepository flickrPhotoRepository;

    public List<FlickrId> fetchPhotosIds() {

        return StreamSupport.stream(flickrPhotoRepository.findAll().spliterator(), false)
                .map(FlickrPhoto::getFlickrId)
                .collect(Collectors.toList());
    }
}
