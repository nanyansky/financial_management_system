package com.nanyan.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author nanyan
 * @version 1.0
 * @description: User实体类
 * @date 2023/3/19 10:57
 */

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
    @Column(name = "register_time")
    private Timestamp registerTime;

    @Column(name = "is_admin")
    private int isAdmin;

    @Column(name = "status")
    private int status;
    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone_number")
    private String phoneNumber;

}
