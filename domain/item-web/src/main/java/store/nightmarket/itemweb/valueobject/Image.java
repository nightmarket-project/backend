package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

public class Image {

    private final String url;
    private final int order;

    public Image(
        String url,
        int order
    ) {
        this.url = url;
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return order == image.order && Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, order);
    }

}
