package com.nanyan.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "stock_trade")
public class StockTrade {

  @Id
  @Column(name = "id")
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "stock_code")
  private String stockCode;
  @Column(name = "stock_name")
  private String stockName;
  @Column(name = "stock_price")
  private double stockPrice;
  @Column(name = "stock_num")
  private int stockNum;
  @Column(name = "trade_price")
  private double tradePrice;
  @Column(name = "trade_time")
  private java.sql.Timestamp tradeTime;
  @Column(name = "trade_type")
  private int tradeType;


}
