package com.example.kaju.Service;

import com.example.kaju.Dtos.UserDto;
import com.example.kaju.Exception.ErrorMessages;
import com.example.kaju.Exception.ResourceAlreadyExistsException;
import com.example.kaju.Exception.ResourceNotFoundException;
import com.example.kaju.Model.User;
import com.example.kaju.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id))
                .viewAsUserDto();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::viewAsUserDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto createUser(User user) {
        if(userRepository.findById(user.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS + ": " + user.getId());
        }
        return userRepository.save(user).viewAsUserDto();
    }

    @Override
    @Transactional
    public UserDto updateUser(long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id));
        
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        
        return userRepository.save(existingUser).viewAsUserDto();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ": " + id));
        userRepository.delete(user);
    }
}
