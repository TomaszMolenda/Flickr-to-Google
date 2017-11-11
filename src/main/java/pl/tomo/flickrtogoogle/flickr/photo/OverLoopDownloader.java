package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.tomo.flickrtogoogle.flickr.set.dto.FlickrPhotoSetDto;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class OverLoopDownloader implements FlickPhotoDownloader {

    @Override
    public List<Photo> download(Flickr flickr, FlickrPhotoSetDto flickrPhotoSetDto) {

        List<Photo> photos = new ArrayList<>();
        int page = 1;
        do {
//

            try {
                final List<Photo> download = download(flickr, flickrPhotoSetDto.getFlickrId(), page);
                photos.addAll(download);

            } catch (FlickrException e) {

                return photos;
            }

            page++;

        } while (true);



//        return IntStream.range(1, flickrPhotoSetDto.getMediaCount() / PER_PAGE + 1)
//                .mapToObj(page -> download(flickr, flickrPhotoSetDto.getFlickrId(), page))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
    }


    private List<Photo> download(Flickr flickr, String flickrId, int page) throws FlickrException {

        log.info("Downloading photos from photosetId " + flickrId + ", page " + page);

        return flickr
                .getPhotosetsInterface()
                .getPhotos(flickrId, Integer.MAX_VALUE, page);

    }
}
