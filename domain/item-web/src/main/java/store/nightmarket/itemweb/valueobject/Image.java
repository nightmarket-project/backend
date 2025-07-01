package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

public class Image {

    private final String url;
    private final int sortNum;

    public Image(
        String url,
        int sortNum
    ) {
        this.url = url;
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return sortNum == image.sortNum && Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, sortNum);
    }

}
