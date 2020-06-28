package com.offcn.springbootsdut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TbUser extends BaseModel {
  private long id;
  private String username;
  private String password;
  private String phone;
  private String email;
  private java.sql.Timestamp created;
  private java.sql.Timestamp updated;
  private String sourceType;
  private String nickName;
  private String name;
  private String status;
  private String headPic;
  private String qq;
  private double accountBalance;
  private String isMobileCheck;
  private String isEmailCheck;
  private String sex;
  private long userLevel;
  private long points;
  private long experienceValue;
  private java.sql.Timestamp birthday;
  private java.sql.Timestamp lastLoginTime;
}
