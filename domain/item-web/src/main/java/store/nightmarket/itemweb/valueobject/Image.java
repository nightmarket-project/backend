package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

public class Image {

    private final String url;

    public Image(
        String url
    ) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

}
