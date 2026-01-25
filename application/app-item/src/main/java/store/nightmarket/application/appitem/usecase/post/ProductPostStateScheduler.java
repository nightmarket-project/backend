package store.nightmarket.application.appitem.usecase.post;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.domain.itemweb.model.ProductPost;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductPostStateScheduler {

	private final ReadProductPostPort readProductPostPort;
	private final SaveProductPostPort saveProductPostPort;

	@Transactional
	@Scheduled(cron = "${schedules.cron}")// 1분마다
	public void refreshPostStatus() {
		LocalDateTime now = LocalDateTime.now();

		List<ProductPost> posts = readProductPostPort.readRefreshProductPost(now);

		posts.forEach(post -> {
			post.refreshStatus(now);
			saveProductPostPort.save(post);
		});

		log.info("scheduler executed: {}", now);
	}

}