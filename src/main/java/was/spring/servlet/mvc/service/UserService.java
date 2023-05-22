package was.spring.servlet.mvc.service;

import was.spring.servlet.mvc.controller.dto.UserJoinDto;
import was.spring.servlet.mvc.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinDto userJoinDto) {
        userRepository.save(userJoinDto.toUser());
    }
}
