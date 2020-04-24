package com.nosql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Set;

@Entry(base = "ou=users", objectClasses = {"top", "inetOrgPerson", "person", "organizationalPerson",
        "simpleSecurityObject"
})
public class User {

    @JsonIgnore
    @Id
    private Name id;

    @JsonProperty("userName")
    private @Attribute(name = "uid")
    String uid;

    //  @Transient
    @JsonProperty(value = "userPassword", access = JsonProperty.Access.WRITE_ONLY)
    private @Attribute(name = "userPassword")
    String userPassword;

    @JsonProperty("ou")
    private @Attribute(name = "ou")
    Set<String> organizationUnit;

    @JsonProperty("firstName")
    private @Attribute(name = "cn")
    String firstName;


    private @Attribute(name = "displayName")
    String displayName;

    @JsonProperty("lastName")
    private @Attribute(name = "sn")
    String lastName;


    public User(String uid, String firstName, String displayName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.displayName = displayName;
        this.lastName = lastName;
    }

    public User(String userName, String firstName, String lastName) {
        this.uid = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String uid, String userPassword, String firstName, String displayName, String lastName) {
        this.uid = uid;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.displayName = displayName;
        this.lastName = lastName;
    }

    public User(String uid, String userPassword, Set<String> organizationUnit, String firstName, String displayName, String lastName) {
        // System.out.println("baseDn: " + baseDn);
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", "users")
                .add("uid", uid)
                .build();
        this.id = dn;
        this.uid = uid;
        this.userPassword = userPassword;
        this.organizationUnit = organizationUnit;
        this.firstName = firstName;
        this.displayName = displayName;
        this.lastName = lastName;
    }


    public User() {

    }

    //    @JsonIgnore
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<String> getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(Set<String> organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", organizationUnit=" + organizationUnit +
                ", firstName='" + firstName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
