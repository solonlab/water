/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package wateradmin;

import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.net.http.PreheatUtils;
import org.noear.solon.core.event.EventBus;
import org.noear.solonx.licence.LicenceInfo;
import org.noear.water.WW;
import org.noear.water.WaterClient;
import org.noear.water.protocol.ProtocolHub;
import org.noear.water.protocol.solution.LogSourceFactoryImpl;
import org.noear.water.protocol.solution.MsgBrokerFactoryImpl;
import org.noear.water.track.TrackBuffer;
import wateradmin.dso.CacheUtil;
import wateradmin.dso.InitPlugin;
import wateradmin.dso.db.DbWaterCfgApi;

public class WateradminApp {
    public static void main(String[] args) throws Exception {
        Solon.start(WateradminApp.class, args, x -> {
            //加载环境变量(支持弹性容器设置的环境)
            x.cfg().loadEnv("water.");

            //避免补排除
            LicenceInfo.check();

            x.pluginAdd(0, new InitPlugin());

            //设置接口
            //
            Config.tryInit(x);
            TrackBuffer.singleton().bind(Config.rd_track);

            ProtocolHub.config = WaterClient.Config::get;

            ProtocolHub.logSourceFactory = new LogSourceFactoryImpl(Config.water_log_store, DbWaterCfgApi::getLogger);
            ProtocolHub.msgBrokerFactory = new MsgBrokerFactoryImpl(Config.water_msg_store, CacheUtil.data, DbWaterCfgApi::getBroker);
        });

        //尝试注册 gritapi 服务
        gritApiRegTry();

        //尝试预热
        PreheatUtils.preheat(WW.path_run_check);
        PreheatUtils.preheat("/login");

        //尝试升级
        Upgrade.tryUpdate();
    }

    /**
     * 尝试注册 gritapi 服务
     */
    private static void gritApiRegTry() {
        try {
            if (CloudClient.discovery() != null) {
                Instance instance = new Instance("gritapi",
                        Instance.local().host(),
                        Instance.local().port()).protocol("http")
                        .metaPut("provider","wateradmin");
                CloudClient.discovery().register("grit", instance);
            }
        } catch (Exception e) {
            EventBus.publish(e);
        }
    }
}