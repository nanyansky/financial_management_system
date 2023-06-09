package com.nanyan.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "income")
public class Income {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "user_name")
  private String userName;
  @Column(name = "income_type_id")
  private int incomeTypeId;
  @Column(name = "income_time")
  private java.sql.Timestamp incomeTime;
  @Column(name = "create_time")
  private Timestamp createTime;
  @Column(name = "income_content")
  private String incomeContent;
  @Column(name = "income_amount")
  private double incomeAmount;
  @Column(name = "is_deleted")
  private int isDeleted;

}
