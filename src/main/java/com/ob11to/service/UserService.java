package com.ob11to.service;

import com.ob11to.dao.UserRepository;
import com.ob11to.dto.UserReadDto;
import com.ob11to.entity.User;
import com.ob11to.mapper.Mapper;
import com.ob11to.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public Optional<UserReadDto> findById(Long id){
        return findById(id, userReadMapper);
    }

    public <T> Optional<T> findById(Long id, Mapper<User,T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("withCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    public boolean delete(Long id){
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
