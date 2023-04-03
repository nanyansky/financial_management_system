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
  @Column(name = "income_time")
  private Timestamp incomeTime;
  @Column(name = "income_source")
  private String incomeSource;
  @Column(name = "income_amount")
  private double incomeAmount;
  @Column(name = "is_deleted")
  private int isDeleted;

}
