package cn.bugstack.xfg.dev.tech.trigger.event;

import com.lmax.disruptor.EventHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XxxEventHandler implements EventHandler<XxxEventHandler.Message> {

    @Override
    public void onEvent(Message longEvent, long l, boolean b) throws Exception {
        log.info("接收消息：{}", longEvent.getValue());
    }

    @Data
    public static class Message {
        private String value;
    }

}
