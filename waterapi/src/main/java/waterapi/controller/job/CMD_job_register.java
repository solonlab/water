/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package waterapi.controller.job;

import org.noear.snack4.ONode;
import org.noear.snack4.codec.TypeRef;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Result;
import org.noear.solon.validation.annotation.NotEmpty;
import org.noear.solon.validation.annotation.Whitelist;
import org.noear.water.model.JobM;
import waterapi.controller.UapiBase;
import waterapi.dso.LockUtils;
import waterapi.dso.db.DbPassApi;
import waterapi.dso.interceptor.Logging;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author noear 2021/5/26 created
 */
@Logging
@Whitelist
@Controller
public class CMD_job_register extends UapiBase {
    /**
     * @param tag     分类标签
     * @param service 服务名
     * @param jobs    任务-json
     */
    @NotEmpty({"tag", "service", "jobs"})
    @Mapping("/job/register/")
    public Result cmd_exec(String tag, String service, String jobs) throws Exception {
        if (jobs.startsWith("{") == false && jobs.startsWith("[") == false) {
            return Result.failure("Wrong parameter @jobs");
        }


        if (LockUtils.tryLock(Solon.cfg().appName(), ("job_register_" + tag + "_" + service), 30)) {
            ONode oNode = ONode.ofJson(jobs);
            Date nTime = new Date();

            //兼容旧的方案
            if (oNode.isObject()) {
                Map<String, String> jobMap = oNode.toBean(Map.class);
                for (Map.Entry<String, String> kv : jobMap.entrySet()) {
                    DbPassApi.addJob(tag, service, kv.getKey(), null, kv.getValue(), nTime);
                }
            }

            if (oNode.isArray()) {
                List<JobM> jobList = oNode.toBean(new TypeRef<List<JobM>>() { });
                for (JobM job : jobList) {
                    DbPassApi.addJob(tag, service, job.name, job.cron7x, job.description, nTime);
                }
            }
        }

        return Result.succeed();
    }
}
