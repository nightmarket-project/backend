package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadReviewImageUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.model.ImageManager;

@Service
@RequiredArgsConstructor
public class ReadReviewImageUseCase implements BaseUseCase<Input, Output> {

	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public Output execute(Input input) {
		List<ImageManager> imageManagerList = readImageManagerPort.readIdList(input.idList());

		return Output.builder()
			.imageManagerList(imageManagerList)
			.build();
	}
}
