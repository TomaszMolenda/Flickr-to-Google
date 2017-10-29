package pl.tomo.flickrtogoogle.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.Size;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.photosets.PhotosetsInterface;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;

@Slf4j
@Service
public class FlickrUploader {

    private final FlickrService flickrService;

    @Autowired
    FlickrUploader(FlickrService flickrService) {
        this.flickrService = flickrService;
    }


    @SneakyThrows
    public byte[] upload() {

        Flickr flickr = flickrService.getFlickr();
        Auth auth = flickrService.getAuth();

        PhotosetsInterface photosetsInterface = flickr.getPhotosetsInterface();

        Collection<Photoset> photosets = photosetsInterface.getList(auth.getUser().getId()).getPhotosets();

        photosets.forEach(
                photoset -> log.info("{}, {}", photoset.getTitle(), photoset.getId()));

        log.info(photosetsInterface.getPhotosetCount(auth.getUser().getId())+"");

        PhotoList<Photo> photos = photosetsInterface.getPhotos("72157641109646773", 10, 1);

        System.out.println(photos.size());

//        System.out.println(photo.getTitle());

        InputStream imageAsStream = flickr.getPhotosInterface().getImageAsStream(new Photo(), Size.ORIGINAL);

        return IOUtils.toByteArray(imageAsStream);
    }


}
