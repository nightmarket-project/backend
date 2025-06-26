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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Image other = (Image) obj;
        return Objects.equals(url, other.url) && order == other.order;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

}
