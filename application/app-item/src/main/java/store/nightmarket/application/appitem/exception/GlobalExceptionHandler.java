package store.nightmarket.application.appitem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import store.nightmarket.domain.item.exception.PreemptionException;
import store.nightmarket.itemweb.exception.ItemWebException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ItemWebException.class)
	protected ResponseEntity<String> handleItemWebException(ItemWebException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(e.getMessage());
	}

	@ExceptionHandler(PreemptionException.class)
	protected ResponseEntity<String> handlePreemptionException(PreemptionException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(e.getMessage());
	}

	@ExceptionHandler(DistributedLockException.class)
	protected ResponseEntity<String> handleDistributedLockException(DistributedLockException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(e.getMessage());
	}

}
