package com.examplePayPal.web.controller;

import com.examplePayPal.service.IUserService;
import com.examplePayPal.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> result = this.service.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = {"/{username}"})
    @ResponseBody
    public ResponseEntity getIdByUsername(@PathVariable String username) {
        UserDto result = this.service.getIdByUsername(username);
        return ResponseEntity.ok(result.getId());
    }
    //        UserDto result = this.service.getIdByUsername(username);
    //        return result.getId();

    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        UserDto result = this.service.create(dto);
        return ResponseEntity.ok(result);
    }
}
