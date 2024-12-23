package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.model.UserModel;
import kr.gymbuddyback.repository.UserRepository;
import kr.gymbuddyback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public UserRepository userRepository;

    @Override
    public UserModel register(UserModel userModel) {


        return null;
    }

    @Override
    public Boolean findEmail(String email) {
        Boolean find = userRepository.existsByEmail(email);
        if (find.equals(true)) {
            return false;
        } else {
            return true;
        }
    }

}

