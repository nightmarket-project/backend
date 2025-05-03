package store.nightmarket.itemweb.valueobject;

import store.nightmarket.itemcore.valueobject.Name;

import java.util.Objects;

public class Image {

    private final String url;
    private final Name name;
    private final int order;

    public Image(
            String url,
            Name name,
            int order
    ) {
        this.url = url;
        this.name = name;
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
