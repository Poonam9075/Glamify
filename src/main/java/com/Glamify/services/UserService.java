
//public interface UserService {
//    void activateUser(String email);
//}


package com.Glamify.services;

import com.Glamify.entities.User;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long userId);

    void activateUser(Long userId);

}
