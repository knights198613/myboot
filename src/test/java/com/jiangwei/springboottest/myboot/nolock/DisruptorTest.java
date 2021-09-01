package com.jiangwei.springboottest.myboot.nolock;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;

import java.util.concurrent.ThreadFactory;

/**
 * @author: weijiang
 * @date: 2021/8/10
 * @description: Disruptor无锁组件测试
 **/
public class DisruptorTest {

    public static void main(String[] args) {
        DisruptorTest disruptorTest = new DisruptorTest();
        try {
            disruptorTest.runTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void runTest() throws InterruptedException {
        LongEventFactory longEventFactory = new LongEventFactory();
        ThreadFactory threadFactory = r -> new Thread(r);
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(longEventFactory, 8, threadFactory);
        //挂载消费者处理函数
        disruptor.handleEventsWith(new LongEventConsumer());
        //启动组件
        disruptor.start();

        //获取组件的ringBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        for(int x=0; true; x++) {
            long index = ringBuffer.next();
            try {
                LongEvent longEvent = ringBuffer.get(index);
                longEvent.setValue(x);
            } finally {
                ringBuffer.publish(index);
            }
            Thread.sleep(1000);
        }

    }

    /**
     * 数据封装对象
     */
    @Data
    class LongEvent {
        private long value;
    }

    /**
     * 数据封装对象工厂，用于生产数据封装对象
     */
    class LongEventFactory implements EventFactory {
        @Override
        public Object newInstance() {
            return new LongEvent();
        }
    }

    /**
     * 数据消费者
     */
    class LongEventConsumer implements EventHandler<LongEvent> {
        @Override
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
            System.out.println("event = " + event.getValue() + ", sequence = " + sequence + ", endOfBatch = " + endOfBatch);
        }
    }
}
