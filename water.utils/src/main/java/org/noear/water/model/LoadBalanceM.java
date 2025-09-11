/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package org.noear.water.model;

import org.noear.water.utils.HostUtils;
import org.noear.water.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author noear 2022/4/15 created
 */
public class LoadBalanceM implements Supplier<String> {
    private final String[] nodes;
    private final int count;

    private int index;
    private static final int indexMax = 99999999;

    public LoadBalanceM(String... servers) {
        List<String> nodeList = new ArrayList<>();

        for (String server : servers) {
            if (TextUtils.isNotEmpty(server)) {
                nodeList.add(HostUtils.adjust(server));
            }
        }


        count = nodeList.size();
        nodes = new String[count];
        nodeList.toArray(nodes);
    }

    @Override
    public String get() {
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return nodes[0];
        } else {
            //这里不需要原子性，快就好
            if (index > indexMax) {
                index = 0;
            }

            return nodes[index++ % count];
        }
    }
}
