package com.nosql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

@Entry(objectClasses = {"top", "organizationalUnit"}, base = "ou=groups")
public class Group {
    @JsonIgnore
    @Id
    private Name dn;

    @JsonProperty("ou")
    private @Attribute(name = "ou")
    String nameOfOrganizationUnit;

    @JsonProperty("description")
    private @Attribute(name = "description")
    String groupDescription;

    public Group() {
    }

    public Group(String ou, String description) {
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", ou)
                .build();
        this.dn = dn;
        this.nameOfOrganizationUnit = ou;
        this.groupDescription = description;
    }


    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getNameOfOrganizationUnit() {
        return nameOfOrganizationUnit;
    }

    public void setNameOfOrganizationUnit(String nameOfOrganizationUnit) {
        this.nameOfOrganizationUnit = nameOfOrganizationUnit;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String description) {
        this.groupDescription = description;
    }

    @Override
    public String toString() {
        return "Group{" +
                "dn=" + dn +
                ", nameOfOrganizationUnit='" + nameOfOrganizationUnit + '\'' +
                ", groupDescription='" + groupDescription + '\'' +
                '}';
    }
}
