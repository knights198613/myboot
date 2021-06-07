package com.jiangwei.springboottest.myboot.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: weijiang
 * @date: 2021/6/7
 * @description: sentinel 测试
 **/
public class SentinelTest {

    public static void main(String[] args) {
        initRules();
        while (true) {
            try(Entry entry = SphU.entry("HelloWorld")) {
                System.out.println("Hello world #####");
            }catch (BlockException e) {
                System.out.println("blocked!!");
            }
        }

    }


    /**
     * 初始化流控规则
     */
    private static void initRules() {
        List<FlowRule> flowRuleList = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        //设置保护资源的名称
        flowRule.setResource("HelloWorld");
        //设置流控的维度：QPS
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置阈值为20
        flowRule.setCount(20);
        flowRuleList.add(flowRule);
        //将流控规则设置到流控管理对象
        FlowRuleManager.loadRules(flowRuleList);
    }
}
