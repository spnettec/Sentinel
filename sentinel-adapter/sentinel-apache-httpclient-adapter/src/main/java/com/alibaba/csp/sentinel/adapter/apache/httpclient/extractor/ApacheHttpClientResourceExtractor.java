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
package com.alibaba.csp.sentinel.adapter.apache.httpclient.extractor;

import org.apache.hc.core5.http.ClassicHttpRequest;

/**
 * Apache HttpClient 5 资源名称提取器接口
 *
 * @author zhaoyuguang
 * @author modified for HttpClient 5
 */
public interface ApacheHttpClientResourceExtractor {

    /**
     * 从 HTTP 请求中提取 Sentinel 资源名称
     *
     * @param request HttpClient 5 的 ClassicHttpRequest 对象
     * @return 资源名称字符串
     */
    String extractor(ClassicHttpRequest request);
}