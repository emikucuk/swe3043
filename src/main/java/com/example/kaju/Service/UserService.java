package com.example.kaju.Service;

import com.example.kaju.Model.User;
import com.example.kaju.Dtos.UserDto;
import java.util.List;

public interface UserService {
    UserDto getUserById(long id);
    List<UserDto> getAllUsers();
    UserDto createUser(User user);
    UserDto updateUser(long id, User user);
    void deleteUser(long id);
}