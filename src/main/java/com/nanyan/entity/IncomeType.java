package com.nanyan.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "income_type")
public class IncomeType {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "is_deleted")
  private int isDeleted;
  @Column(name = "create_time")
  private java.sql.Timestamp createTime;

}
