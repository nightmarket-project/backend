package store.nightmarket.itemweb.valueobject;

import java.util.Objects;

public class Image {

    private String url;
    private String altText;
    private int sortNum;

    public Image(
        String url,
        String altText,
        int sortNum
    ) {
        this.url = url;
        this.altText = altText;
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return sortNum == image.sortNum && Objects.equals(url, image.url)
            && Objects.equals(altText, image.altText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, altText, sortNum);
    }

}
