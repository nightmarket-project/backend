package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ReadProductPostImageUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.ImageManager;

@Service
@RequiredArgsConstructor
public class ReadProductPostImageUseCase implements BaseUseCase<Input, Output> {

	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public Output execute(Input input) {
		List<ImageManager> imageManagerList = readImageManagerPort.readImageTypeList(
			input.id(),
			input.imageTypeList()
		);

		return Output.builder()
			.imageManagerList(imageManagerList)
			.build();
	}

}
