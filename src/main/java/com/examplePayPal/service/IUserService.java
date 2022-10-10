package com.examplePayPal.service;

import com.examplePayPal.web.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAll();
    public UserDto getIdByUsername(String username);
    public UserDto create(UserDto dto);
    public UserDto updateBalancce(UserDto dto);
}
