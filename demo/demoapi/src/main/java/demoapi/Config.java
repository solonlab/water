/*
 *  Copyright 2017-2025 noear.org
 *
 *  企业用户未经 noear 组织特别许可，需遵循 AGPL-3.0 开源协议合理合法使用本项目。
 *
 *   Enterprise users are required to use this project reasonably
 *   and legally in accordance with the AGPL-3.0 open source agreement
 *  without special permission from the smartboot organization.
 */
package demoapi;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.cloud.impl.CloudI18nBundleFactory;
import org.noear.solon.i18n.I18nBundleFactory;

/**
 * @author noear 2022/4/8 created
 */
@Configuration
public class Config {
    //将国际化配置切换到 water 管理
    @Bean
    public I18nBundleFactory i18nBundleFactory(){
        return new CloudI18nBundleFactory();
    }
}
