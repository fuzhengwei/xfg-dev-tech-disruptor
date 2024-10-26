package cn.bugstack.xfg.dev.tech.config;

import cn.bugstack.xfg.dev.tech.trigger.event.XxxEventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Bean("xxxEventDisruptor")
    public Disruptor<XxxEventHandler.Message> disruptor() {
        // 环形队列的大小，注意要是2的幂
        int bufferSize = 1024;

        // 创建Disruptor
        Disruptor<XxxEventHandler.Message> disruptor = new Disruptor<>(XxxEventHandler.Message::new, bufferSize, executor);

        // 连接事件处理器
        disruptor.handleEventsWith(new XxxEventHandler());

        // 开始Disruptor
        disruptor.start();

        return disruptor;
    }

}
