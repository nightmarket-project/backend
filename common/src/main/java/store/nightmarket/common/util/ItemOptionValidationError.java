package store.nightmarket.common.util;

import store.nightmarket.common.domain.model.BaseId;

public record ItemOptionValidationError(
        BaseId<?> optionId,
        String message
) {

    @Override
    public String toString() {
        return "ErrorResult [optionId=" + optionId + ", message=" + message + "]";
    }

}
