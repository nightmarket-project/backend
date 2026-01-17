package store.nightmarket.application.appitem.usecase.product;

import static store.nightmarket.application.appitem.usecase.product.dto.RegisterProductUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

@Service
@RequiredArgsConstructor
public class RegisterProductUseCase implements BaseUseCase<Input, Void> {

	private final SaveProductPort saveProductPort;

	@Override
	public Void execute(Input input) {
		saveProductPort.save(
			Product.newInstance(
				new ProductId(UUID.randomUUID()),
				new UserId(input.userId().getId()),
				input.name(),
				input.description(),
				input.price()
			)
		);
		return null;
	}

}
