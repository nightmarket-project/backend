package store.nightmarket.common.util;

import store.nightmarket.common.domain.model.BaseId;

public record ItemOptionValidationError(
        String name,
        String message
) {

    @Override
    public String toString() {
        return "ErrorResult [name=" + name + ", message=" + message + "]";
    }

}
