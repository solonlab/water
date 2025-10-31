/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.dso;

import org.noear.snack4.ONode;
import org.noear.water.WaterAddress;
import org.noear.water.model.JobM;
import org.noear.water.utils.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分布式任务服务接口
 *
 * @author noear
 * @since 2.0
 */
public class JobApi {
    protected final ApiCaller apiCaller;

    public JobApi() {
        apiCaller = new ApiCaller(WaterAddress.getDefApiUrl());
    }


    /**
     * 注册任务
     *
     * @param jobs [name,]
     */
    public boolean register(String tag, String service, List<JobM> jobs) throws IOException {
        if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(service)){
            return false;
        }

        if(jobs == null || jobs.size() == 0){
            return false;
        }

        String jobs_str = ONode.serialize(jobs);

        Map<String, String> params = new HashMap<>();
        params.put("tag", tag);
        params.put("service", service);
        params.put("jobs", jobs_str);

        String txt = apiCaller.post("/job/register/", params);

        int code = ONode.ofJson(txt).get("code").getInt();
        return code == 1 || code == 200;
    }
}
