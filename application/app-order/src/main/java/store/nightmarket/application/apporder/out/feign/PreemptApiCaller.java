package store.nightmarket.application.apporder.out.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.nightmarket.application.apporder.out.feign.PreemptApiDto.PreemptResponse;

@FeignClient(name = "preemptApi", url = "${feign.preempt-api.url}")
public interface PreemptApiCaller {

	@PostMapping("api/v1/preempt")
	PreemptResponse preemptRequest(@RequestBody PreemptApiDto.PreemptRequest request);

}
