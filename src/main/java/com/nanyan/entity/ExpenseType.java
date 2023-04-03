package com.nanyan.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "expense_type")
public class ExpenseType {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "is_deleted")
  private int isDeleted;

  @Column(name = "create_time")
  private Timestamp createTime;

}
