package pl.tomo.flickrtogoogle.flickr.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.set.dto.FlickrPhotoSetDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlickrPhotoSetExtractor {

    private final FlickrPhotoSetRepository flickrPhotoSetRepository;
    private final FlickrPhotoSetDtoFactory flickrPhotoSetDtoFactory;

    @Autowired
    FlickrPhotoSetExtractor(FlickrPhotoSetRepository flickrPhotoSetRepository, FlickrPhotoSetDtoFactory flickrPhotoSetDtoFactory) {
        this.flickrPhotoSetRepository = flickrPhotoSetRepository;
        this.flickrPhotoSetDtoFactory = flickrPhotoSetDtoFactory;
    }

    public List<FlickrPhotoSetDto> extractPhotoSets() {

        return flickrPhotoSetRepository.findAll()
                .stream()
                .filter(flickrPhotoSet -> !flickrPhotoSet.getFlickrId().equals("72157652250253836"))
                .map(flickrPhotoSetDtoFactory::create)
                .collect(Collectors.toList());
    }
}
