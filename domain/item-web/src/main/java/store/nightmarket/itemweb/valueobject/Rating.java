package store.nightmarket.itemweb.valueobject;

import store.nightmarket.itemweb.exception.ItemWebException;

import java.util.Objects;

public class Rating {

    private int value;

    public Rating(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if(!(value >= 0 && value <= 5)) {
            throw new ItemWebException("Rating must be between 0 and 5");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rating other = (Rating) obj;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
