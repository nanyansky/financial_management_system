package com.nanyan.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/3/23 10:19
 */
@Entity
@Data
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private int isAdmin;

//    @Column(name = "is_deleted")
//    private int isDeleted;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone_number")
    private String phoneNumber;

}
