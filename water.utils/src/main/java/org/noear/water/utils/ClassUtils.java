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

/**
 * @author noear 2021/3/25 created
 */
public class ClassUtils {
    public static String formatClassName(String className) {
        if (TextUtils.isEmpty(className)) {
            return "";
        }

        String[] ss = className.split("\\.");

        StringBuilder sb = new StringBuilder(className.length());
        for (int i = 0, len = ss.length; i < len; i++) {
            if (i > (len - 2)) {
                sb.append(ss[i]).append('.');
            } else {
                sb.append(ss[i].charAt(0)).append('.');
            }
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
