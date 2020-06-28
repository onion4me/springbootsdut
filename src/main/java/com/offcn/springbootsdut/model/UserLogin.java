package com.offcn.springbootsdut.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin extends BaseModel {
    private String checkCode;
}
