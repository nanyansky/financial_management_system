package com.nanyan.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "expense")
public class Expense {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "user_name")
  private String userName;
  @Column(name = "expense_type_id")
  private int expenseTypeId;
  @Column(name = "expense_time")
  private java.sql.Timestamp expenseTime;
  @Column(name = "create_time")
  private Timestamp createTime;
  @Column(name = "expense_content")
  private String expenseContent;
  @Column(name = "expense_amount")
  private double expenseAmount;
  @Column(name = "is_deleted")
  private int isDeleted;

}
