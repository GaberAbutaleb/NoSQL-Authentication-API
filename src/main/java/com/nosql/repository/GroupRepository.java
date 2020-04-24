package com.nosql.repository;

import com.nosql.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class GroupRepository {

    @Autowired
    private LdapTemplate ldapTemplate;

    public Group create(Group group) {
        Group newGroup = new Group(group.getNameOfOrganizationUnit(), group.getGroupDescription());
        ldapTemplate.create(newGroup);
        return group;
    }


    public Group findBy(String ouName) {
        return ldapTemplate.findOne(query().where("ou").is(ouName), Group.class);
    }

    public Group update(Group group, String ou) {
        Group oldGroupInfo = this.findBy(ou);
        oldGroupInfo.setGroupDescription(group.getGroupDescription());
        ldapTemplate.update(oldGroupInfo);
        return group;
    }

    public void delete(Group group) {
        ldapTemplate.delete(group);
    }

    public List<Group> findAll() {
        return ldapTemplate.findAll(Group.class);
    }

}
