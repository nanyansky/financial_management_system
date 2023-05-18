package com.nanyan.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_debt")
public class UserDebt {

  @Id
  @Column(name = "debt_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int debtId;
  @Column(name = "debt_name")
  private String debtName;
  @Column(name = "debt_type")
  private int debtType;
  @Column(name = "debt_create_time")
  private java.sql.Timestamp debtCreateTime;
  @Column(name = "debt_price")
  private double debtPrice;
  @Column(name = "debt_owner_name")
  private String debtOwnerName;
  @Column(name = "debt_owner_id")
  private int debtOwnerId;
  @Column(name = "debt_remark")
  private String debtRemark;
  @Column(name = "debt_status")
  private int debtStatus;
  @Column(name = "is_deleted")
  private int isDeleted;

}
