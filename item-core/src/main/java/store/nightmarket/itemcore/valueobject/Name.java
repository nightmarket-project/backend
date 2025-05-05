package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.itemcore.exception.NameException;

import java.util.Objects;

@Getter
public class Name {

    private final String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String name) {
        if (name == null) {
            throw new NameException("이름은 null이 안된다");
        }
        if (name.isBlank()) {
            throw new NameException("Name cannot be blank");
        }
        if (name.length() > 64) {
            throw new NameException("Name cannot be longer than 64 characters");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Name other = (Name) obj;
        return Objects.equals(value, other.value);
    }
}
