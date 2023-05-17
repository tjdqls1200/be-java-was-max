package was.container.mvc.service;

import was.container.mvc.controller.dto.UserJoinDto;
import was.container.mvc.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinDto userJoinDto) {
        userRepository.save(userJoinDto.toUser());
    }
}
