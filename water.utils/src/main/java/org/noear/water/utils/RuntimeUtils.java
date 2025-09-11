/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.utils;

import java.lang.management.*;
import java.text.DecimalFormat;
import java.util.*;
import com.sun.management.OperatingSystemMXBean;

public class RuntimeUtils {
    private static RuntimeStatus status = new RuntimeStatus();


    public static RuntimeStatus getStatus() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();

        Runtime runtime = Runtime.getRuntime();

        status.memoryTotal = (byteToM(runtime.totalMemory()));
        status.memoryUsed = (byteToM(runtime.totalMemory() - runtime.freeMemory()));


        // 总的物理内存
        String memoryMax = new DecimalFormat("#.##")
                .format(systemMXBean.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
        // 剩余的物理内存
        String memoryFree = new DecimalFormat("#.##")
                .format(systemMXBean.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";

        status.system.put("arch",systemMXBean.getArch());
        status.system.put("systemLoadAverage",systemMXBean.getSystemLoadAverage());
        status.system.put("availableProcessors",systemMXBean.getAvailableProcessors());
        status.system.put("memoryMax",memoryMax);
        status.system.put("memoryFree",memoryFree);


        List<Map<String,Object>> memoryPools = new ArrayList<>();

        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        if(pools != null && !pools.isEmpty()) {
            for (final MemoryPoolMXBean pool : pools) {
                MemoryUsage usage = pool.getUsage();
                Map<String, Object> map = new LinkedHashMap<>();

                map.put("name", pool.getName());
                map.put("max", byteToM(usage.getMax()));
                map.put("init", byteToM(usage.getInit()));
                map.put("used", byteToM(usage.getUsed()));

                memoryPools.add(map);
            }
        }

        status.memoryPools = memoryPools;


        status.timeElapsed = (runtimeMXBean.getUptime());

        status.threadCount = (threadMXBean.getThreadCount());
        status.threadPeakCount = (threadMXBean.getPeakThreadCount());
        status.threadDaemonCount = (threadMXBean.getDaemonThreadCount());

        if(status.pid == null) {
            //这些只需要取一次
            //
            status.memoryMax = (byteToM(runtime.maxMemory()));

            status.timeStart = new Datetime(runtimeMXBean.getStartTime()).toString();

            status.pid = runtimeMXBean.getName();
            status.os = (System.getProperty("os.name"));
            status.vm = (System.getProperty("java.vm.vendor")
                    + " / " + System.getProperty("java.vm.name")
                    + " / " + System.getProperty("java.vm.version"));
        }


        return status;
    }

    public static long byteToM(long bytes) {
        long kb = (bytes / 1024 / 1024);
        return kb;
    }
}
