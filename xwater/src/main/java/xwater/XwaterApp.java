/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package xwater;

import org.noear.solon.Solon;
import org.noear.solon.core.NvMap;
import org.noear.solonx.licence.LicenceInfo;

public class XwaterApp {
    public static void main(String[] args) {
        NvMap argx = NvMap.from(args);
        argx.put("debug","1");

        Solon.start(XwaterApp.class, argx, x -> {
            //加载环境变量(支持弹性容器设置的环境)
            x.cfg().loadEnv("water.");

            //避免补排除
            LicenceInfo.check();
        });
    }
}