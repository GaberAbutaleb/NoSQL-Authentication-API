package com.nosql.controller;

import com.nosql.logging.AuthenticationInfo;
import com.nosql.model.Group;
import com.nosql.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/group")
@CrossOrigin(origins = "*")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AuthenticationInfo authenticationInfo;

    @GetMapping(path = "/{ou}")
    public ResponseEntity<Group> getGroup(@PathVariable("ou") String ou) {
        Group group = null;
        try {
            group = groupRepository.findBy(ou);
            logger.info("login user " + authenticationInfo.getUsername() + " get Group with ou " + group.getNameOfOrganizationUnit());
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to get Group with ou " + group.getNameOfOrganizationUnit());
        }
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }


    @GetMapping(path = "/all")
    public ResponseEntity<List<Group>> getAllGroup() {
        List<Group> allgroup = null;
        try {
            allgroup = groupRepository.findAll();
            logger.info("Login user " + authenticationInfo.getUsername() + " get all Group ");

        } catch (Exception ex) {
            logger.info("Error when login user " + authenticationInfo.getUsername() + " try to get all Group ");
        }
        return new ResponseEntity<List<Group>>(allgroup, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{ou}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String ou) {

        try {

            Group group = groupRepository.findBy(ou);
            groupRepository.delete(group);
            logger.info("login user " + authenticationInfo.getUsername() + " delete group with ou " + ou);
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to delte Group with ou " + ou);
        }
        return new ResponseEntity<>("Group is deleted Successfully", HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody Group group) {
        Group newGroup = null;
        try {
            newGroup = groupRepository.create(group);
            logger.info("login user " + authenticationInfo.getUsername() + " create new group with ou " + group.getNameOfOrganizationUnit());
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to delte Group with ou " + group.getNameOfOrganizationUnit());
        }

        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{ou}")
    public ResponseEntity<Object> updateUser(@PathVariable("ou") String ou, @RequestBody Group group) {
        Group updateGroup = null;
        try {
            updateGroup = groupRepository.update(group, ou);
            logger.info("login user " + authenticationInfo.getUsername() + " update new  group with ou " + group.getNameOfOrganizationUnit());
        } catch (Exception ex) {
            logger.error("Error when login user " + authenticationInfo.getUsername() + " try to update Group with ou " + group.getNameOfOrganizationUnit());
        }

        return new ResponseEntity<>(updateGroup, HttpStatus.OK);
    }
}
