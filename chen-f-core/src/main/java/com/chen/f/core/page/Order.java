package com.chen.f.core.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 顺序
 *
 * @author chen
 * @since 2020/4/23 16:11.
 */
@Getter
@Setter
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    private String column;

    /**
     * 顺序类型
     */
    private OrderTypeEnum orderType;

    public Order(String column, OrderTypeEnum orderType) {
        this.column = column;
        this.orderType = orderType;
    }

    public static Order create(String column, OrderTypeEnum orderType) {
        return new Order(column, orderType);
    }

}
