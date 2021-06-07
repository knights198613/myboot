package com.jiangwei.springboottest.myboot.current.forkjoin;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author: weijiang
 * @date: 2021/5/24
 * @description: 实现订单信息拼装的任务类
 **/
public class OrderInfoTask extends RecursiveTask<OrderInfo> {

    @Override
    protected OrderInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        CustomerInfoTask customerInfoTask = new CustomerInfoTask();
        PriceInfoTask priceInfoTask = new PriceInfoTask();
        OrderItemInfoTask orderItemInfoTask = new OrderItemInfoTask();
        DiscountInfoTask discountInfoTask = new DiscountInfoTask();
        OtherInfoTask otherInfoTask = new OtherInfoTask();
        //调用所有子任务执行
        invokeAll(customerInfoTask, priceInfoTask, orderItemInfoTask, discountInfoTask, otherInfoTask);
        //join合并所有子任务执行结果
        OrderInfo orderInfo = new OrderInfo(customerInfoTask.join(), priceInfoTask.join(), orderItemInfoTask.join(),
                discountInfoTask.join(), otherInfoTask.join());
        return orderInfo;
    }


    public static void main(String[] args) {
        //当前容器cpu核数-1
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-1);
        //调用订单信息的任务，并join（合并）结果，main线程被阻塞等待任务完成
        OrderInfo orderInfo = forkJoinPool.invoke(new OrderInfoTask());
        System.out.println("执行"+ OrderInfoTask.class.getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        System.out.println(JSON.toJSONString(orderInfo));
    }
}

class CustomerInfoTask extends RecursiveTask<CustomerInfo> {
    @Override
    protected CustomerInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("Thomas Tom");
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return customerInfo;
    }
}
class PriceInfoTask extends RecursiveTask<PriceInfo> {
    @Override
    protected PriceInfo compute() {
        System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPrice(45.66D);
        return priceInfo;
    }
}
class OrderItemInfoTask extends RecursiveTask<List<OrderItemInfo>> {
    @Override
    protected List<OrderItemInfo> compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        List<OrderItemInfo> orderItemInfos = new ArrayList<>();
        OrderItemInfo itemInfo1 = new OrderItemInfo();
        itemInfo1.setItemName("黑人牙膏");
        OrderItemInfo itemInfo2 = new OrderItemInfo();
        itemInfo2.setItemName("雀巢咖啡袋装");
        orderItemInfos.add(itemInfo1);
        orderItemInfos.add(itemInfo2);
        return orderItemInfos;
    }
}
class DiscountInfoTask extends RecursiveTask<List<DiscountInfo>> {
    @Override
    protected List<DiscountInfo> compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        List<DiscountInfo> discountInfos = new ArrayList<>();
        DiscountInfo discountInfo1 = new DiscountInfo();
        discountInfo1.setActivityDiscount(1.23D);
        DiscountInfo discountInfo2 = new DiscountInfo();
        discountInfo2.setActivityDiscount(2.22D);
        discountInfos.add(discountInfo1);
        discountInfos.add(discountInfo2);
        return discountInfos;
    }
}
class OtherInfoTask extends RecursiveTask<OtherInfo> {
    @Override
    protected OtherInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        OtherInfo otherInfo = new OtherInfo();
        otherInfo.setOtherInfo("this other infos");
        return otherInfo;
    }
}








/**
 * 订单信息
 */
@Data
class OrderInfo {
    CustomerInfo customerInfo;
    PriceInfo priceInfo;
    List<OrderItemInfo> orderItemInfoList;
    List<DiscountInfo> discountInfoList;
    OtherInfo otherInfo;

    public OrderInfo(CustomerInfo customerInfo, PriceInfo priceInfo, List<OrderItemInfo> orderItemInfoList,
                     List<DiscountInfo> discountInfoList, OtherInfo otherInfo) {
        this.customerInfo = customerInfo;
        this.priceInfo = priceInfo;
        this.orderItemInfoList = orderItemInfoList;
        this.discountInfoList = discountInfoList;
        this.otherInfo = otherInfo;
    }
}

/**
 * 用户信息
 */
@Data
class CustomerInfo {
    private String name;
}

/**
 * 价格信息
 */
@Data
class PriceInfo {
    private Double price;
}

/**
 * 订单项信息
 */
@Data
class OrderItemInfo {
    private String itemName;
}

/**
 * 促销优惠信息
 */
@Data
class DiscountInfo {
    private Double activityDiscount;
}

/**
 * 其他信息
 */
@Data
class OtherInfo{
    private String otherInfo;
}

