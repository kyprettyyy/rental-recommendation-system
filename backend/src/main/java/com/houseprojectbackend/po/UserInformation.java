package com.houseprojectbackend.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@ToString
@Data
@Entity
@Table(name = "userInformation")
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账户
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "pass")
    private String pass;

    public UserInformation(Integer id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public UserInformation() {
    }
}

