package pl.tomo.flickrtogoogle.flickr.adapters.outgoing;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public enum MediaType {

    PHOTO("image/jpeg"),
    VIDEO(EMPTY);

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
