package com.examplePayPal.service.impl;

import com.examplePayPal.dao.entity.User;
import com.examplePayPal.dao.repository.UserRepo;
import com.examplePayPal.service.IUserService;
import com.examplePayPal.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo repository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public List<UserDto> getAll() {
        List<User> result=this.repository.findAll();
        List<UserDto> resultDto=result.stream()
                .map((User user)->{
                return this.mapper.map(user, UserDto.class);
            }).collect(Collectors.toList());
            return resultDto;
    }

    @Override
    public UserDto getIdByUsername(String username){
        UserDto dto=null;
        Optional<User> result= Optional.ofNullable(this.repository.findIdByUsername(username));
        dto=this.mapper.map(result.get(), UserDto.class);
        return dto;
    }

    @Override
    public UserDto create(UserDto dto) {
        User entity=this.mapper.map(dto, User.class);
        entity=this.repository.save(entity);
        return this.mapper.map(entity, UserDto.class);
    }
}
