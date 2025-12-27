package store.nightmarket.application.appitem.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;

@Service
@RequiredArgsConstructor
public class RegisterProductUseCase implements BaseUseCase<RegisterProductUseCaseDto.Input, Void> {

	private final SaveProductPort saveProductPort;

	@Override
	public Void execute(RegisterProductUseCaseDto.Input input) {
		saveProductPort.save(
			Product.newInstance(
				new ProductId(UUID.randomUUID()),
				input.name(),
				input.description(),
				input.price()
			)
		);
		return null;
	}

}
