package com.nosql.controller;

import com.nosql.logging.AuthenticationInfo;
import com.nosql.model.User;
import com.nosql.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationInfo authenticationInfo;

    @GetMapping(path = "/{uid}")
    public ResponseEntity<User> getUser(@PathVariable("uid") String uid) {
        User user = null;
        try {
            user = userRepository.findByUid(uid);
            logger.info("login user " + authenticationInfo.getUsername() + " get user with uid " + uid);
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to get user with uid " + uid);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> alluser = null;
        try {
            alluser = userRepository.findAll();
            logger.info("Login user " + authenticationInfo.getUsername() + " get all users ");

        } catch (Exception ex) {
            logger.info("Error when login user " + authenticationInfo.getUsername() + " try to get all users ");
        }

        return new ResponseEntity<List<User>>(alluser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{uid}")
    public ResponseEntity<Object> deleteUser(@PathVariable String uid) {

        try {
            User user = userRepository.findByUid(uid);
            userRepository.delete(user);
            logger.info("login user " + authenticationInfo.getUsername() + " delete user with uid " + uid);
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to delte user with uid " + uid);
        }

        return new ResponseEntity<>("User " + uid + " is deleted successfully", HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User newUser = null;
        try {
            newUser = userRepository.create(user);
            logger.info("login user " + authenticationInfo.getUsername() + " create new user with uid " + user.getUid());
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to delte user with uid " + user.getUid());
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Object> updateUser(@PathVariable("uid") String uid, @RequestBody User user) {
        User updateuser = null;
        try {
            updateuser = userRepository.update(user, uid);
            logger.info("login user " + authenticationInfo.getUsername() + " update new  with uid " + uid);
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to update with uid " + uid);
        }
        return new ResponseEntity<>(updateuser, HttpStatus.OK);
    }
}
