package com.nanyan.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "user_stock")
public class UserStock {

  @Id
  @Column(name = "stock_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long stockId;
  private String stockCode;
  private String stockName;
  private String stockType;
  private double stockPrice;
  private long stockNum;
  private Timestamp stockTime;
  private String stockUser;
  private long stockUserId;

}
