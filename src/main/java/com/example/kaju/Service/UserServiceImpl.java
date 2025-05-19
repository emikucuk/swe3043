package com.example.kaju.Service;

import com.example.kaju.Dtos.UserDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public UserDto getUserById(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id);
        }
        return user.viewAsUserDto();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return users.values()
                .stream()
                .map(User::viewAsUserDto)
                .toList();
    }

    @Override
    public UserDto createUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new ResourceAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS + ": " + user.getId());
        }
        users.put(user.getId(), user);
        return user.viewAsUserDto();
    }

    @Override
    public UserDto updateUser(long id, User updatedUser) {
        User existingUser = users.get(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id);
        }
        
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        
        users.put(id, existingUser);
        return existingUser.viewAsUserDto();
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) {
            throw new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id);
        }
        users.remove(id);
    }
}
