package kr.gymbuddyback.service;


import kr.gymbuddyback.model.UserModel;

public interface UserService {
    UserModel register(UserModel userModel);

    Boolean findEmail(String email);
}
