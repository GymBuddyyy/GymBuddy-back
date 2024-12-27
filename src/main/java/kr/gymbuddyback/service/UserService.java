package kr.gymbuddyback.service;


import kr.gymbuddyback.entity.UserEntity;
import kr.gymbuddyback.model.UserModel;

public interface UserService {

    Boolean findEmail(String email);

    void joinUser(UserModel user);

    UserEntity getUserInfo(String email);
}
