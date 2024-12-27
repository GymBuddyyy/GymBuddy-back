package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.entity.UserEntity;
import kr.gymbuddyback.model.UserModel;
import kr.gymbuddyback.repository.UserRepository;
import kr.gymbuddyback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void joinUser(UserModel user) {
        UserEntity userEntity = UserEntity.toUserEntity(user);
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserInfo(String email) {
        UserEntity user = userRepository.findByUserEmail(email);
        return user;
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

