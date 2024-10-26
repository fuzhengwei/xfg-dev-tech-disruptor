package cn.bugstack.xfg.dev.test.trigger;

import cn.bugstack.xfg.dev.tech.Application;
import cn.bugstack.xfg.dev.tech.trigger.event.XxxEventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DisruptorTest {

    @Resource
    private Disruptor<XxxEventHandler.Message> xxxEventDisruptor;

    @Test
    public void test_publishEvent() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            xxxEventDisruptor.publishEvent((event, sequence) -> event.setValue("你好，我是 Disruptor Message"));
        }

        // 暂停 - 测试完手动关闭程序
        new CountDownLatch(1).await();
    }

}
