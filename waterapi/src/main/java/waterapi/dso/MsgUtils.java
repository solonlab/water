/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package waterapi.dso;

import org.noear.water.WW;
import org.noear.water.protocol.ProtocolHub;

public class MsgUtils {
    public static void updateCache(String tags) {
        //
        //初始化时，注册自己会造成缓存更新；此时 messageSource 还未初始化
        //
        if (ProtocolHub.msgBrokerFactory == null) {
            return;
        }

        try {

            ProtocolHub.getMsgSource(null).addMessage(WW.msg_ucache_topic, tags);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
