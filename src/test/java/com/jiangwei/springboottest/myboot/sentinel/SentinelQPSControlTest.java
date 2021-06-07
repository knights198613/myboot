package com.jiangwei.springboottest.myboot.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: weijiang
 * @date: 2021/6/7
 * @description: Sentinel基于QPS的流控测试
 **/
public class SentinelQPSControlTest {

    //资源名称
    private static String key_resource = "abc";

    private static AtomicInteger done = new AtomicInteger(0);

    private static AtomicInteger pass = new AtomicInteger(0);

    private static AtomicInteger block = new AtomicInteger(0);

    //测试QPS值
    private static int requestQps = 100;
    //流控规则阈值
    private static int count = 10;


    public static void main(String[] args) throws InterruptedException {
        System.out.println("test pace flow control start");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        initPaceFlowRule();
        simulateFlowControl(countDownLatch);
        countDownLatch.await();
        System.out.println("total pass:"+ pass.get()+", total block:"+block.get());
        System.out.println("test pace flow end!!!!!!!!!!");




        System.out.println("test default flow control start");
        done.set(0);
        pass.set(0);
        block.set(0);
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        initDefaultFlowRule();
        simulateFlowControl(countDownLatch1);
        countDownLatch1.await();
        System.out.println("total pass:"+ pass.get()+", total block:"+block.get());
        System.out.println("test default flow control end######");

    }


    //构造匀速通过规则
    private static void initPaceFlowRule() {
        List<FlowRule> flowRuleList = new ArrayList<>();
        FlowRule paceRule = new FlowRule(key_resource);
        paceRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        paceRule.setCount(count);
        paceRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        paceRule.setLimitApp("default");
        paceRule.setMaxQueueingTimeMs(20 * 1000);
        flowRuleList.add(paceRule);
        FlowRuleManager.loadRules(flowRuleList);
    }

    /**
     * 构造默认的限量规则
     */
    private static void initDefaultFlowRule() {
        List<FlowRule> flowRuleList = new ArrayList<>();
        FlowRule defaultRule = new FlowRule(key_resource);
        defaultRule.setCount(count);
        defaultRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        defaultRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        defaultRule.setLimitApp("default");
        flowRuleList.add(defaultRule);
        FlowRuleManager.loadRules(flowRuleList);
    }

    /**
     * 模拟流控过程
     *
     * @param countDownLatch
     */
    private static void simulateFlowControl(CountDownLatch countDownLatch) {
        for (int i = 0; i < requestQps; i++) {
            Thread t = new Thread(() -> {
                long startMs = System.currentTimeMillis();
                Entry entry = null;
                try {
                    entry = SphU.entry(key_resource);
                    pass.incrementAndGet();
                } catch (BlockException e) {
                    e.printStackTrace();
                    block.incrementAndGet();
                } finally {
                    if (entry != null) {
                        entry.close();
                    }
                    done.incrementAndGet();
                    long cost = System.currentTimeMillis() - startMs;
                    System.out.println(System.currentTimeMillis() + ", one request pass, cost:" + cost);
                }

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (done.get() >= requestQps) {
                        countDownLatch.countDown();
                    }
                }


            }, "myThread_" + i);

            t.start();
        }
    }


}
