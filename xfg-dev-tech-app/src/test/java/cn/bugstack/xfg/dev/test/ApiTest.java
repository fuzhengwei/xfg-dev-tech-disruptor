package cn.bugstack.xfg.dev.test;

import cn.bugstack.xfg.dev.tech.Application;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApiTest {

    ExecutorService executor = Executors.newCachedThreadPool();

    @Test
    public void test() {
        // 创建工厂
        LongEventFactory factory = new LongEventFactory();

        // 创建环形队列的大小，注意要是2的幂
        int bufferSize = 1024;

        // 创建Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // 连接事件处理器
        disruptor.handleEventsWith(new LongEventHandler());

        // 开始Disruptor
        disruptor.start();

        // 发布事件
        // 这里简单地发布一个事件
        disruptor.publishEvent((event, sequence) -> event.setValue(100L));

        // 关闭
        disruptor.shutdown();
        executor.shutdown();
    }

    public static class LongEventHandler implements EventHandler<LongEvent> {
        @Override
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
            System.out.println("Event: " + event.getValue());
        }
    }

    public static class LongEventFactory implements EventFactory<LongEvent> {
        @Override
        public LongEvent newInstance() {
            return new LongEvent();
        }
    }

    public static class LongEvent {
        private long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

}
