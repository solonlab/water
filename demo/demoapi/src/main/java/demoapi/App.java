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

import org.noear.solon.Solon;
import org.noear.solon.web.cors.CrossInterceptor;

/**
 * @author noear 2021/11/7 created
 */
public class App {
    public static void main(String[] args){
        Solon.start(App.class, args, app->{
            //添加跨域支持
            app.routerInterceptor(new CrossInterceptor());
        });
    }
}
