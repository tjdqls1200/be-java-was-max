package board.mvc.service;

import board.mvc.controller.dto.UserJoinDto;
import board.mvc.controller.dto.UserLoginDto;
import board.mvc.controller.dto.UserSessionDto;
import board.mvc.domain.User;
import board.mvc.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinDto userJoinDto) {
        userRepository.save(userJoinDto.toUser());
    }

    public UserSessionDto login(UserLoginDto userLoginDto) {
        final User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(IllegalArgumentException::new);

        if (user.isMatchedPassword(userLoginDto.getPassword())) {
            return UserSessionDto.from(user);
        }

        throw new IllegalArgumentException();
    }
}
