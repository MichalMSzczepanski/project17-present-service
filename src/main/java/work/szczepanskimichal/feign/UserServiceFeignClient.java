package work.szczepanskimichal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import work.szczepanskimichal.model.user.UserCommsDto;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "user-service", url = "${services.address.user}")
public interface UserServiceFeignClient {

    @GetMapping("/v1/internal/user/comms/{userId}")
    Optional<UserCommsDto> getUserComms(@PathVariable UUID userId);

}
