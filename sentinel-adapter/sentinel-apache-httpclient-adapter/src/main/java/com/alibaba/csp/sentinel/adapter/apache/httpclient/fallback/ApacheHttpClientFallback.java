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
package com.alibaba.csp.sentinel.adapter.apache.httpclient.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.hc.client5.http.classic.ExecChain;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;

/**
 * Apache HttpClient 5 限流降级回退接口
 *
 * @author zhaoyuguang
 * @author modified for HttpClient 5
 */
@FunctionalInterface
public interface ApacheHttpClientFallback {

    /**
     * 处理被限流的请求
     *
     * @param request  原始的 HTTP 请求
     * @param scope    执行链作用域，包含路由、客户端上下文等信息
     * @param ex       限流异常
     * @return 自定义的响应
     * @throws IOException   IO异常
     * @throws HttpException HTTP异常
     */
    ClassicHttpResponse handle(ClassicHttpRequest request,
                               ExecChain.Scope scope,
                               BlockException ex) throws IOException, HttpException;

    /**
     * 简化的处理方法，如果不需使用 ExecChain.Scope
     *
     * @param request 原始的 HTTP 请求
     * @param ex      限流异常
     * @return 自定义的响应
     * @throws IOException   IO异常
     * @throws HttpException HTTP异常
     */
    default ClassicHttpResponse handle(ClassicHttpRequest request,
                                       BlockException ex) throws IOException, HttpException {
        return handle(request, null, ex);
    }
}