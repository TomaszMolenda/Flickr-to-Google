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

    private final FlickrServiceFactory flickrServiceFactory;
    private final FlickrStorage flickrStorage;

    @Autowired
    FlickrUploader(FlickrServiceFactory flickrServiceFactory, FlickrStorage flickrStorage) {
        this.flickrServiceFactory = flickrServiceFactory;
        this.flickrStorage = flickrStorage;
    }


    @SneakyThrows
    public byte[] upload() {

        FlickrService flickrService = flickrServiceFactory.create();

        Auth auth = flickrService.getAuth();
        Flickr flickr = flickrService.getFlickr();

        PhotosetsInterface photosetsInterface = flickr.getPhotosetsInterface();

        Collection<Photoset> photosets = photosetsInterface.getList(auth.getUser().getId()).getPhotosets();

        photosets.forEach(flickrStorage::savePhotoSet);

        log.info(photosetsInterface.getPhotosetCount(auth.getUser().getId())+"");

        PhotoList<Photo> photos = photosetsInterface.getPhotos("72157641109646773", 10, 1);

        System.out.println(photos.size());

//        System.out.println(photo.getTitle());

        InputStream imageAsStream = flickr.getPhotosInterface().getImageAsStream(photos.get(0), Size.ORIGINAL);

        return IOUtils.toByteArray(imageAsStream);
    }
}
