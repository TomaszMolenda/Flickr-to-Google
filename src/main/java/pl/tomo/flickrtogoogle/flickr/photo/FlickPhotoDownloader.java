package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.photos.Photo;
import pl.tomo.flickrtogoogle.flickr.set.dto.FlickrPhotoSetDto;

import java.util.List;

public interface FlickPhotoDownloader {

    List<Photo> download(Flickr flickr, FlickrPhotoSetDto flickrPhotoSetDto);
}
