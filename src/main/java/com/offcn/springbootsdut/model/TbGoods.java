package com.sample;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbGoods {
  private long id;
  private String sellerId;
  private String goodsName;
  private long defaultItemId;
  private String auditStatus;
  private String isMarketable;
  private long brandId;
  private String caption;
  private long category1Id;
  private long category2Id;
  private long category3Id;
  private String smallPic;
  private double price;
  private long typeTemplateId;
  private String isEnableSpec;
  private String isDelete;
}
