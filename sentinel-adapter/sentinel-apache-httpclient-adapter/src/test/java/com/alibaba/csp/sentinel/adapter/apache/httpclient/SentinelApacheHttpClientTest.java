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

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.app.TestApplication;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.config.SentinelApacheHttpClientConfig;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.extractor.ApacheHttpClientResourceExtractor;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Apache HttpClient 5 Sentinel 适配器单元测试
 *
 * @author zhaoyuguang
 * @author modified for HttpClient 5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8184"
        })
public class SentinelApacheHttpClientTest {

    @Value("${server.port}")
    private Integer port;

    @Test
    public void testSentinelOkHttpInterceptor0() throws Exception {
        try (CloseableHttpClient httpclient = new SentinelApacheHttpClientBuilder().build()) {
            HttpGet httpGet = new HttpGet("http://localhost:" + port + "/httpclient/back");
            System.out.println(getRemoteString(httpclient, httpGet));
            ClusterNode cn = ClusterBuilderSlot.getClusterNode("httpclient:/httpclient/back");
            assertNotNull("ClusterNode should not be null", cn);
            Constants.ROOT.removeChildList();
            ClusterBuilderSlot.getClusterNodeMap().clear();
        }
    }

    @Test
    public void testSentinelOkHttpInterceptor1() throws Exception {
        SentinelApacheHttpClientConfig config = new SentinelApacheHttpClientConfig();
        config.setExtractor(new ApacheHttpClientResourceExtractor() {

            @Override
            public String extractor(ClassicHttpRequest request) {
                String contains = "/httpclient/back/";
                String path = request.getPath();
                if (path != null && path.startsWith(contains)) {
                    path = path.substring(0, path.indexOf(contains) + contains.length()) + "{id}";
                }
                return request.getMethod() + ":" + path;
            }
        });

        try (CloseableHttpClient httpclient = new SentinelApacheHttpClientBuilder(config).build()) {
            HttpGet httpGet = new HttpGet("http://localhost:" + port + "/httpclient/back/1");
            System.out.println(getRemoteString(httpclient, httpGet));
            ClusterNode cn = ClusterBuilderSlot.getClusterNode("httpclient:GET:/httpclient/back/{id}");
            assertNotNull("ClusterNode should not be null", cn);
            Constants.ROOT.removeChildList();
            ClusterBuilderSlot.getClusterNodeMap().clear();
        }
    }

    private String getRemoteString(CloseableHttpClient httpclient, HttpGet httpGet) throws IOException {
        HttpContext context = HttpClientContext.create();

        return httpclient.execute(httpGet, context, response -> {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            return result;
        });
    }
}
