package com.ob11to.service;

import com.ob11to.dao.UserRepository;
import com.ob11to.dto.UserReadDto;
import com.ob11to.entity.User;
import com.ob11to.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;


    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::mapFrom);
    }

    public boolean delete(Long id){
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
