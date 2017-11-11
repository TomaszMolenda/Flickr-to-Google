package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.photos.Photo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.service.FlickrServiceCreator;
import pl.tomo.flickrtogoogle.flickr.set.FlickrPhotoSet;
import pl.tomo.flickrtogoogle.flickr.set.FlickrPhotoSetExtractor;
import pl.tomo.flickrtogoogle.flickr.set.FlickrPhotoSetRepository;
import pl.tomo.flickrtogoogle.flickr.set.dto.FlickrPhotoSetDto;

import java.util.List;

@Service
@Log
public class FlickrPhotoCreator {

    private final FlickrPhotoSetExtractor flickrPhotoSetExtractor;
    private final FlickrServiceCreator flickrServiceCreator;
    private final FlickrPhotoFactory flickrPhotoFactory;
    private final FlickrPhotoExistChecker flickrPhotoExistChecker;
    private final FlickrPhotoRepository flickrPhotoRepository;
    private final FlickrPhotoSetRepository flickrPhotoSetRepository;
    private final OverLoopDownloader overLoopDownloader;

    @Autowired
    FlickrPhotoCreator(FlickrPhotoSetExtractor flickrPhotoSetExtractor, FlickrServiceCreator flickrServiceCreator, FlickrPhotoFactory flickrPhotoFactory, FlickrPhotoExistChecker flickrPhotoExistChecker, FlickrPhotoRepository flickrPhotoRepository, FlickrPhotoSetRepository flickrPhotoSetRepository, OverLoopDownloader overLoopDownloader) {
        this.flickrPhotoSetExtractor = flickrPhotoSetExtractor;
        this.flickrServiceCreator = flickrServiceCreator;
        this.flickrPhotoFactory = flickrPhotoFactory;
        this.flickrPhotoExistChecker = flickrPhotoExistChecker;
        this.flickrPhotoRepository = flickrPhotoRepository;
        this.flickrPhotoSetRepository = flickrPhotoSetRepository;
        this.overLoopDownloader = overLoopDownloader;
    }


    public void create() {

        final Flickr flickr = flickrServiceCreator.create().getFlickr();

        flickrPhotoSetExtractor.extractPhotoSets()
                .stream()
//                .filter(this::notCompleteDownload)
                .forEach(flickrPhotoSetDto -> create(flickrPhotoSetDto, flickr));
    }

    private boolean notCompleteDownload(FlickrPhotoSetDto flickrPhotoSetDto) {

        return flickrPhotoSetDto.getMediaCount() != flickrPhotoSetDto.getFlickrPhotosIds().size();
    }

    private void create(FlickrPhotoSetDto flickrPhotoSetDto, Flickr flickr) {

        final List<Photo> photos = overLoopDownloader.download(flickr, flickrPhotoSetDto);

        for (Photo photo : photos) {

            final FlickrPhoto flickrPhoto = flickrPhotoFactory.create(photo);

            if (flickrPhotoExistChecker.notExist(flickrPhoto)) {

                save(flickrPhoto, flickrPhotoSetDto);
            }

        }

//        flickPhotoDownloader.download(flickr, flickrPhotoSetDto)
//                .stream()
//                .map(flickrPhotoFactory::create)
//                .filter(flickrPhotoExistChecker::notExist)
//                .forEach(flickrPhoto -> save(flickrPhoto, flickrPhotoSetDto));
    }

    private void save(FlickrPhoto flickrPhoto, FlickrPhotoSetDto flickrPhotoSetDto) {

        log.info("Save photo " + flickrPhoto.getFlickrId() + ", in photoset " + flickrPhotoSetDto.getFlickrId());

        flickrPhotoRepository.save(flickrPhoto);

        final FlickrPhotoSet flickrPhotoSet = flickrPhotoSetRepository.findFirstByFlickrId(flickrPhotoSetDto.getFlickrId());

        flickrPhotoSet.addPhoto(flickrPhoto);

        flickrPhotoSetRepository.save(flickrPhotoSet);
    }
}
