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
  private int stockId;
  @Column(name = "stock_code")
  private String stockCode;
  @Column(name = "stock_name")
  private String stockName;
  @Column(name = "stock_price")
  private double stockPrice;
  @Column(name = "stock_num")
  private int stockNum;
  @Column(name = "stock_time")
  private Timestamp stockTime;
  @Column(name = "stock_user")
  private String stockUser;
  @Column(name = "stock_user_id")
  private int stockUserId;

}
