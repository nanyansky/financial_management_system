package com.nanyan.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_assets")
public class UserAssets {

  @Id
  @Column(name = "assets_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int assetsId;
  @Column(name = "assets_name")
  private String assetsName;
  @Column(name = "assets_location")
  private String assetsLocation;
  @Column(name = "assets_create_time")
  private java.sql.Timestamp assetsCreateTime;
  @Column(name = "assets_price")
  private double assetsPrice;
  @Column(name = "assets_owner_id")
  private int assetsOwnerId;
  @Column(name = "assets_owner_name")
  private String assetsOwnerName;
  @Column(name = "assets_remark")
  private String assetsRemark;
  @Column(name = "is_deleted")
  private int isDeleted;

}
