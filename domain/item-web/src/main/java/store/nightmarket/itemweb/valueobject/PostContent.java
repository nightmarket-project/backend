package store.nightmarket.itemweb.valueobject;

import store.nightmarket.itemweb.exception.ItemWebException;

import java.util.List;
import java.util.Objects;

public class PostContent {

    private final static int MAX_TEXT_LENGTH = 255;
    private final static int MAX_IMAGE_SIZE = 10;

    private final String text;
    private final List<Image> imageList;

    public PostContent(
            String text,
            List<Image> imageList
    ) {
        this.text = text;
        this.imageList = imageList;
        validate();
    }

    private void validate() {
        if (text.isBlank()) {
            throw new ItemWebException("Text is blank");
        }
        if (text.length() > MAX_TEXT_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
        if (imageList.size() > MAX_IMAGE_SIZE) {
            throw new ItemWebException("Image size is too many");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PostContent other = (PostContent) obj;
        return Objects.equals(text, other.text) && Objects.equals(imageList, other.imageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, imageList);
    }

}
