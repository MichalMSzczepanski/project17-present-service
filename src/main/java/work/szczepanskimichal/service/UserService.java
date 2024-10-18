package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.UserNotFoundException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.model.user.UserCommsDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceFeignClient userServiceFeignClient;

    public UserCommsDto getUserComms(UUID userId) {
        return userServiceFeignClient.getUserComms(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

}
