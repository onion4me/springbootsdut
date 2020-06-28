package com.offcn.springbootsdut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel implements Serializable {
    private int code;
    private String message;
}
