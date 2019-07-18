package com.lyzb.jbx.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :TYK
 * Date: 2019/5/27  11:42
 * Desc:
 */
public class ListChangeIndexUtil {

    /**
     * 将一个集合中的数据拿来插入其他index  不是交换位置  比如3 到第一个位置  原来的第一个位置 就变成了2
     * 如  a,b,c  变成  c,a,b   而不是 c，b，a
     * @param list
     * @param index1
     * @param index2
     * @param <E>
     */
    public static <E> void swap(List<E> list, int index1, int index2) {
        List<E> list1 = new ArrayList<>();
        //定义第三方变量
        E e = list.get(index1);

        list.remove(e);
        list1 = list;
        list1.add(index2,e);
    }

}
