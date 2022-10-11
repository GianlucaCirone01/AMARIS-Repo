package com.examplePayPal.service;

import com.examplePayPal.dao.entity.User;
import com.examplePayPal.web.dto.TransferDto;
import com.examplePayPal.web.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> getAll();
    public UserDto getById(int id) throws Exception;
    public UserDto getIdByUsername(String username);
    public UserDto create(UserDto dto);
    public UserDto updateBalancce(UserDto dto);
    public void transaction(TransferDto dto) throws Exception;
}
