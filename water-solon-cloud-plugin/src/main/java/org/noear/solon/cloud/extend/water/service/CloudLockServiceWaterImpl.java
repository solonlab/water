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

import org.noear.solon.cloud.service.CloudLockService;
import org.noear.water.utils.LockUtils;

/**
 * 分布式锁服务
 *
 * @author noear
 * @since 1.3
 */
public class CloudLockServiceWaterImpl implements CloudLockService {


    @Override
    public boolean tryLock(String group, String key, int seconds, String holder) {
        if (holder == null) {
            holder = "-";
        }

        return LockUtils.tryLock(group, key, seconds, holder);
    }

    @Override
    public void unLock(String group, String key, String holder) {
        LockUtils.unLock(group, key, holder);
    }

    @Override
    public boolean isLocked(String group, String key) {
        return LockUtils.isLocked(group, key);
    }

    @Override
    public String getHolder(String group, String key) {
        return LockUtils.getLockValue(group, key);
    }
}
