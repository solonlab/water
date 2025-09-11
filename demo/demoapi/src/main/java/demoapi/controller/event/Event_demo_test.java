/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package demoapi.controller.event;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.model.Event;
import org.noear.solon.logging.utils.TagsMDC;

/**
 * @author noear 2021/11/7 created
 */

//消息订阅：订阅消息并处理（根据：topic 进行订阅）
@Slf4j
@CloudEvent("demo.test")
public class Event_demo_test implements CloudEventHandler {
    @Override
    public boolean handle(Event event) throws Exception {
        //处理消息...
        TagsMDC.tag0("msg");

        log.info("我收到消息：" + event.content());
        return true;
    }
}