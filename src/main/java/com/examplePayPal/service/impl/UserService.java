package com.examplePayPal.service.impl;

import com.examplePayPal.dao.entity.User;
import com.examplePayPal.dao.repository.UserRepo;
import com.examplePayPal.exception.ApiRequestException;
import com.examplePayPal.service.IUserService;
import com.examplePayPal.web.dto.TransferDto;
import com.examplePayPal.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public UserDto getById(int id) throws Exception {
        UserDto dto=null;
        Optional<User> result=this.repository.findById(id);
        if(result.isPresent()) {
            dto = this.mapper.map(result.get(), UserDto.class);
        } else {
            throw new Exception(); //TODO: gestione errore
        }
        return dto;
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
        User userNameVerify= this.repository.findByUsername(entity.getUsername());
        if (null==userNameVerify) {
            if (null != dto.getBalance()) {
                entity = this.repository.save(entity);
            } else {
                entity.setBalance(0.0);
                entity = this.repository.save(entity);
            }
            return this.mapper.map(entity, UserDto.class);
        }else
            throw new ApiRequestException("ERROR: Username already present");
    }

    @Override
    public UserDto updateBalancce(UserDto dto) {
        User entity=this.repository.findByUsername(dto.getUsername());
        entity.setBalance(entity.getBalance()+dto.getBalance());
        entity = this.repository.save(entity);
        return this.mapper.map(entity, UserDto.class);
    }


    @Override
    @Transactional
    public void transaction(TransferDto dto) throws Exception {
        UserDto uDto1= getById(dto.getId1());
        UserDto uDto2= getById(dto.getId2());
        User u1=this.mapper.map(uDto1, User.class);
        User u2=this.mapper.map(uDto2, User.class);
        if(u1.getBalance()>=dto.getBalanceToTransfer()){
            u1.setBalance(u1.getBalance()-dto.getBalanceToTransfer());
            u2.setBalance(u2.getBalance()+dto.getBalanceToTransfer());
            u1=this.repository.save(u1);
            u2=this.repository.save(u2);
            System.out.println("OPERAZIONE EFFETTUATA");
        }else{
            System.out.println("ERROR: TRANSAZIONE NON EFFETTUATA");
        }
    }


}
