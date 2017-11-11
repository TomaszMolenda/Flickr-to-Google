package pl.tomo.flickrtogoogle.flickr.set.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FlickrPhotoSetDto {

    private final String flickrId;
    private final Integer mediaCount;
    private final List<String> flickrPhotosIds;

    public FlickrPhotoSetDto(String flickrId, Integer mediaCount, List<String> flickrPhotosIds) {
        this.flickrId = flickrId;
        this.mediaCount = mediaCount;
        this.flickrPhotosIds = flickrPhotosIds;
    }
}
