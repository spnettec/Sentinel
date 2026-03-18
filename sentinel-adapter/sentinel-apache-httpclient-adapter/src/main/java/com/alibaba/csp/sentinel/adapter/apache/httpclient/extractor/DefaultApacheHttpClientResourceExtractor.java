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
 * Apache HttpClient 5 默认资源名称提取器实现
 *
 * @author zhaoyuguang
 * @author modified for HttpClient 5
 */
public class DefaultApacheHttpClientResourceExtractor implements ApacheHttpClientResourceExtractor {

    @Override
    public String extractor(ClassicHttpRequest request) {
        // 获取请求的 URI 路径
        String path = request.getPath();

        // 如果路径为空，返回空字符串
        if (path == null) {
            return "";
        }

        return path;
    }
}