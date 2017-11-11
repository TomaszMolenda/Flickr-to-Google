package pl.tomo.flickrtogoogle.flickr.service;

import org.springframework.stereotype.Service;

@Service
public class FlickrPhotoDownloader {

    public byte[] download(String flickrId) {

        return new byte[]{1};
    }
}
