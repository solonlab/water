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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author noear 2022/4/7 created
 */
public class I18nApi {
    protected final ApiCaller apiCaller;

    public I18nApi() {
        apiCaller = new ApiCaller(WaterAddress.getCfgApiUrl());
    }

    Map<String, Map> i18nMap = Collections.synchronizedMap(new HashMap());

    /**
     * 获取密钥
     */
    public Map getI18n(String tag, String bundle, String lang) throws IOException {
        String i18nKey = String.format("%s:%s:%s", tag, bundle, lang);

        Map map = i18nMap.get(i18nKey);

        if (map == null) {
            synchronized (i18nKey.intern()) {
                map = i18nMap.get(i18nKey);

                if (map == null) {
                    map = loadDo(tag, bundle, lang);
                }
                i18nMap.put(i18nKey, map);
            }
        }

        return map;
    }

    /**
     * 刷新
     */
    public Map getI18nNoCache(String tag, String bundle, String lang) throws IOException {
        Map map = loadDo(tag, bundle, lang);

        if (map.size() > 0) {
            String i18nKey = String.format("%s:%s:%s", tag, bundle, lang);
            i18nMap.put(i18nKey, map);
        }

        return map;
    }

    protected Map<String, String> loadDo(String tag, String bundle, String lang) throws IOException {
        String json = apiCaller.http("/i18n/get/")
                .data("tag", tag)
                .data("bundle", bundle)
                .data("lang", lang)
                .post();

        ONode oNode = ONode.ofJson(json);

        Map<String, String> map;
        int code = oNode.get("code").getInt();
        if (code == 200) {
            map = oNode.get("data").toBean(Map.class);
        } else {
            map = new LinkedHashMap<>();
        }

        return map;
    }
}
