package com.offcn.springbootsdut;

import java.util.ArrayList;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/22
 */
public class SdutArrayList<E> extends ArrayList<E> {

    public void sdut(E e){
        System.out.println(e.hashCode());
    }
}
