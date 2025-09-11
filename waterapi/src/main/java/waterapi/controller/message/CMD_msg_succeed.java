/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package waterapi.controller.message;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Result;
import org.noear.solon.validation.annotation.NotEmpty;
import org.noear.solon.validation.annotation.Whitelist;
import org.noear.water.protocol.ProtocolHub;
import org.noear.water.utils.TextUtils;
import waterapi.controller.UapiBase;
import waterapi.dso.interceptor.Logging;

/**
 * 消息置为成功
 *
 * @author noear
 * @since 2017.07
 * Update time 2020.09
 */
@Logging
@Whitelist
@Controller
public class CMD_msg_succeed extends UapiBase {

    /**
     * @param key            消息key
     * @param subscriber_key 订阅者key
     */
    @NotEmpty("key")
    @Mapping("/msg/succeed/")
    public Result cmd_exec(String broker, String key, String subscriber_key) throws Exception {

        if (TextUtils.isEmpty(subscriber_key)) {
            ProtocolHub.getMsgSource(broker).setMessageAsSucceed(key);
        } else {
            ProtocolHub.getMsgSource(broker).setDistributionAsSucceed(key, subscriber_key);
        }

        return Result.succeed();
    }
}