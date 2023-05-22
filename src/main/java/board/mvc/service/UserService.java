package board.mvc.service;

import board.mvc.controller.dto.UserJoinDto;
import board.mvc.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinDto userJoinDto) {
        userRepository.save(userJoinDto.toUser());
    }
}
