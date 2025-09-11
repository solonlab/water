/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package demoapi.controller.job;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.cloud.CloudJobHandler;
import org.noear.solon.cloud.annotation.CloudJob;
import org.noear.solon.core.handle.Context;
import org.slf4j.MDC;

/**
 * @author noear 2021/11/7 created
 */
//分布式任务
@Slf4j
@CloudJob(name = "demo_test", cron7x = "0 1 * * * ?")
public class Job_test implements CloudJobHandler {

    @Override
    public void handle(Context ctx) throws Throwable {
        //处理任务...
//        TagsMDC
        MDC.put("tag0", "job");
        log.info("我被调度了");
    }
}