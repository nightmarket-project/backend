package store.nightmarket.itemweb.valueobject;

import store.nightmarket.itemweb.exception.ItemWebException;

public record Rating(float value) {

    public Rating {
        validate(value);
    }

    private void validate(float value) {
        if (!(value >= 0 && value <= 5)) {
            throw new ItemWebException("Rating must be between 0 and 5");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rating other = (Rating) obj;
        return value == other.value;
    }

}
