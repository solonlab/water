/*
 * Copyright 2017-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.cloud.extend.water.service;

import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudJobHandler;
import org.noear.solon.cloud.model.JobHolder;
import org.noear.solon.cloud.service.CloudJobService;
import org.noear.solon.core.bean.LifecycleBean;
import org.noear.solon.logging.utils.TagsMDC;
import org.noear.water.WaterClient;
import org.noear.water.model.JobM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分布式任务服务
 *
 * @author noear
 * @since 1.2
 */
public class CloudJobServiceWaterImpl implements CloudJobService , LifecycleBean {
    static final Logger log = LoggerFactory.getLogger(CloudJobServiceWaterImpl.class);

    public static final CloudJobServiceWaterImpl instance = new CloudJobServiceWaterImpl();

    public Map<String, JobHolder> jobMap = new LinkedHashMap<>();

    public JobHolder get(String name) {
        return jobMap.get(name);
    }

    private void push() {
        if (jobMap.size() == 0) {
            return;
        }

        List<JobM> jobs = new ArrayList<>();
        jobMap.forEach((k, v) -> {
            jobs.add(new JobM(v.getName(), v.getCron7x(), v.getDescription()));
        });

        try {
            WaterClient.Job.register(Solon.cfg().appGroup(), Solon.cfg().appName(), jobs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postStart() throws Throwable {
        push();
    }

    @Override
    public boolean register(String name, String cron7x, String description, CloudJobHandler handler) {
        JobHolder jobHolder = new JobHolder(name, cron7x, description, handler);

        jobMap.put(name, jobHolder);
        TagsMDC.tag0("CloudJob");
        log.info("CloudJob: Handler registered name:" + name + ", class:" + handler.getClass().getName());
        TagsMDC.tag0("");
        return true;
    }

    @Override
    public boolean isRegistered(String name) {
        return jobMap.containsKey(name);
    }
}
