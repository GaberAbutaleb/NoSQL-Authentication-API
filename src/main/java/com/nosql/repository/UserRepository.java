package com.nosql.repository;


import com.nosql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class UserRepository {
    @Autowired
    private LdapTemplate ldapTemplate;


    public User update(User user, String uid) {
        User oldUserInfo = this.findByUid(uid);
        if (user.getFirstName() != null) {
            oldUserInfo.setFirstName(user.getFirstName());
        }

        if (user.getDisplayName() != null) {
            oldUserInfo.setDisplayName(user.getDisplayName());
        }

        if (user.getLastName() != null) {
            oldUserInfo.setLastName(user.getLastName());
        }

        if (user.getOrganizationUnit() != null || !user.getOrganizationUnit().isEmpty()) {
            oldUserInfo.setOrganizationUnit(user.getOrganizationUnit());
        }

        ldapTemplate.update(oldUserInfo);
        return user;
    }

    public User findByUid(String uid) {
        return ldapTemplate.findOne(query().where("uid").is(uid), User.class);
    }

    public List<User> findAll() {
        return ldapTemplate.findAll(User.class);
    }

    public void delete(User person) {
        ldapTemplate.delete(person);
    }

    public User create(User user) {
        User newUser = new User(user.getUid(), user.getUserPassword(), user.getOrganizationUnit(), user.getFirstName(), user.getDisplayName(), user.getLastName());
        ldapTemplate.create(newUser);
        return user;
    }
}
