package com.chen.f.core.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 分页
 *
 * @author chen
 * @since 2020/4/23 10:30.
 */
@Getter
@Setter
@ToString
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private long pageIndex = 1;
    /**
     * 每页条数
     */
    private long pageSize = 10;

    /**
     * 数据列表
     */
    private List<T> list = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 顺序列表
     */
    private List<Order> orderList = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    public Page<T> addOrder(Order... items) {
        orderList.addAll(Arrays.asList(items));
        return this;
    }

    public Page<T> addOrder(List<Order> items) {
        orderList.addAll(items);
        return this;
    }

    public Page<T> clearOrder() {
        orderList.clear();
        return this;
    }

    /**
     * 仅用于反序列化
     * <p>
     * eg:name1.ascend-name2.descend
     */
    public Page<T> setSort(String sort) {
        parseOrder(sort, "-", ".", "ascend", "descend");
        return this;
    }

    /**
     * 按照格式解析字符串到顺序列表
     *
     * @param value              需要解析的字符串
     * @param separator          每个排序之间的分隔符
     * @param nameOrderSeparator 字段名称与排序类型之间的分隔符
     * @param asc                升序的标识
     * @param desc               倒序的标识
     */
    public void parseOrder(String value, String separator, String nameOrderSeparator, String asc, String desc) {
        //value eg: name.last.ascend-nat.descend
        final Pattern compile = Pattern.compile(String.format("(?<name>[\\w\\.]*)\\%s(?<order>%s|%s)\\%s?", nameOrderSeparator, asc, desc, separator));

        final Matcher matcher = compile.matcher(value);
        while (matcher.find()) {
            final String name = matcher.group("name");
            final String order = matcher.group("order");

            final OrderTypeEnum orderType;
            if (StringUtils.equals(order, asc)) {
                orderType = OrderTypeEnum.ASC;
            } else if (StringUtils.equals(order, desc)) {
                orderType = OrderTypeEnum.DESC;
            } else {
                orderType = null;
            }

            addOrder(Order.create(name, orderType));
        }
    }


    /**
     * 转化为 mybatis-plus 分页
     *
     * @return mybatis-plus 分页
     */
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> toMybatisPlusPage() {
        final com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> mybatisPlusPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        mybatisPlusPage.setRecords(this.getList());
        mybatisPlusPage.setTotal(this.getTotal());
        mybatisPlusPage.setSize(this.getPageSize());
        mybatisPlusPage.setCurrent(this.getPageIndex());

        mybatisPlusPage.setSearchCount(this.isSearchCount());
        mybatisPlusPage.setOptimizeCountSql(this.isOptimizeCountSql());

        final List<Order> orders = this.getOrderList();
        if (CollectionUtils.isNotEmpty(orders)) {
            final List<OrderItem> orderItemList = orders.stream()
                    .map((order -> {
                        final OrderItem orderItem = new OrderItem();
                        orderItem.setColumn(order.getColumn());
                        orderItem.setAsc(order.getOrderType().isAsc());
                        return orderItem;
                    }))
                    .collect(Collectors.toList());
            mybatisPlusPage.setOrders(orderItemList);
        }
        return mybatisPlusPage;
    }

    /**
     * mybatis-plus 分页 转化为 当前分页
     *
     * @param mybatisPlusPage mybatis-plus 分页
     * @param <T>             分页实体
     * @return 当前分页
     */
    public static <T> Page<T> ofMybatisPlusPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> mybatisPlusPage) {
        final Page<T> page = new Page<>();

        page.setList(mybatisPlusPage.getRecords());
        page.setTotal(mybatisPlusPage.getTotal());
        page.setPageSize(mybatisPlusPage.getSize());
        page.setPageIndex(mybatisPlusPage.getCurrent());
        
        page.setOptimizeCountSql(mybatisPlusPage.optimizeCountSql());
        page.setSearchCount(mybatisPlusPage.searchCount());
        
        final List<OrderItem> orderItemList = mybatisPlusPage.orders();
        if (CollectionUtils.isNotEmpty(orderItemList)) {
            final List<Order> orderList = orderItemList.stream()
                    .map((orderItem -> Order.create(orderItem.getColumn(), orderItem.isAsc() ? OrderTypeEnum.ASC : OrderTypeEnum.DESC)))
                    .collect(Collectors.toList());
            page.setOrderList(orderList);
        }
        
        return page;
    }
}
