/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.adapter.apache.httpclient;

import com.alibaba.csp.sentinel.*;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.config.SentinelApacheHttpClientConfig;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.StringUtil;

import org.apache.hc.client5.http.classic.ExecChain;
import org.apache.hc.client5.http.classic.ExecChainHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;

/**
 * Sentinel Apache HttpClient 5 适配器
 *
 * @author zhaoyuguang
 * @author modified for HttpClient 5
 */
public class SentinelApacheHttpClientBuilder extends HttpClientBuilder {

    private final SentinelApacheHttpClientConfig config;

    public SentinelApacheHttpClientBuilder() {
        this.config = new SentinelApacheHttpClientConfig();
    }

    public SentinelApacheHttpClientBuilder(SentinelApacheHttpClientConfig config) {
        this.config = config;
    }

    @Override
    public CloseableHttpClient build() {
        // 添加 Sentinel 执行链拦截器作为第一个拦截器，确保能捕获所有请求
        addExecInterceptorFirst("sentinel", new SentinelExecChainHandler());
        return super.build();
    }

    /**
     * Sentinel 执行链处理器，用于拦截和处理 HTTP 请求
     */
    private class SentinelExecChainHandler implements ExecChainHandler {

        @Override
        public ClassicHttpResponse execute(
                ClassicHttpRequest request,
                ExecChain.Scope scope,
                ExecChain chain) throws IOException, HttpException {

            Entry entry = null;
            try {
                // 提取资源名称
                String name = config.getExtractor().extractor(request);
                if (!StringUtil.isEmpty(config.getPrefix())) {
                    name = config.getPrefix() + name;
                }

                // 定义 Sentinel 入口
                entry = SphU.entry(name, ResourceTypeConstants.COMMON_WEB, EntryType.OUT);

                // 继续执行链
                return chain.proceed(request, scope);

            } catch (BlockException e) {
                // 处理被限流的请求
                return config.getFallback().handle(request, scope, e);

            } catch (Throwable t) {
                // 记录异常
                if (entry != null) {
                    Tracer.traceEntry(t, entry);
                }
                throw t;

            } finally {
                // 退出 Sentinel 入口
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }
}
