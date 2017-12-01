package pl.tomo.flickrtogoogle.flickr.photo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.Response;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoUtils;
import com.flickr4java.flickr.photos.PhotosInterface;
import lombok.SneakyThrows;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pl.tomo.flickrtogoogle.flickr.service.FlickrService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.api.client.util.Maps.newHashMap;
import static com.google.common.collect.Lists.newArrayList;

public class AllPhotosInterface extends PhotosInterface {

    private static final String FLICKR_PHOTOS_SEARCH = "flickr.photos.search";

    private final String userId;
    private final Flickr flickr;

    AllPhotosInterface(FlickrService flickrService) {

        super(flickrService.getFlickr().getApiKey(),
                flickrService.getFlickr().getSharedSecret(),
                flickrService.getFlickr().getTransport());

        this.userId = flickrService.obtainUserId();
        this.flickr = flickrService.getFlickr();
    }

    public List<Photo> getAll(int perPage, int page) {

        Map<String, Object> parameters = createParameters(perPage, page);

        Response response = connect(parameters);

        Element photoset = response.getPayload();

        NodeList photoElements = photoset.getElementsByTagName("photo");

        final ArrayList<Photo> photos = newArrayList();

        for (int i = 0; i < photoElements.getLength(); i++) {

            Element photoElement = (Element) photoElements.item(i);
            photos.add(PhotoUtils.createPhoto(photoElement, photoset));
        }

        return photos;

    }

    private Map<String, Object> createParameters(int perPage, int page) {

        Map<String, Object> parameters = newHashMap();

        parameters.put("method", FLICKR_PHOTOS_SEARCH);
        parameters.put("user_id", userId);
        parameters.put("per_page", String.valueOf(perPage));
        parameters.put("page", String.valueOf(page));
        parameters.put("sort", "date-posted-desc");
        parameters.put("extras", "date_upload,date_taken");

        return parameters;
    }

    @SneakyThrows
    private Response connect(Map<String, Object> parameters) {

        final Transport transport = flickr.getTransport();
        final String apiKey = flickr.getApiKey();
        final String sharedSecret = flickr.getSharedSecret();

        return transport.get(transport.getPath(), parameters, apiKey, sharedSecret);
    }
}
