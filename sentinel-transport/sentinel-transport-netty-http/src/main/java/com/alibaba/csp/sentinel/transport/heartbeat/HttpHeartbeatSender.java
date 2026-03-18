/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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
package com.alibaba.csp.sentinel.transport.heartbeat;

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.spi.Spi;
import com.alibaba.csp.sentinel.transport.HeartbeatSender;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.transport.endpoint.Protocol;
import com.alibaba.csp.sentinel.transport.heartbeat.client.HttpClientsFactory;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.csp.sentinel.util.PidUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.csp.sentinel.transport.endpoint.Endpoint;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Eric Zhao
 * @author Carpenter Lee
 * @author Leo Li
 */
@Spi(order = Spi.ORDER_LOWEST - 100)
public class HttpHeartbeatSender implements HeartbeatSender {

    private final CloseableHttpClient client;

    private static final int OK_STATUS = HttpStatus.SC_OK;

    private final int timeoutMs = 3000;

    // RequestConfig 用于请求相关的超时配置
    private final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.of(timeoutMs, TimeUnit.MILLISECONDS))
            .setResponseTimeout(Timeout.of(timeoutMs, TimeUnit.MILLISECONDS))
            .build();

    private final Protocol consoleProtocol;
    private final String consoleHost;
    private final int consolePort;

    public HttpHeartbeatSender() {
        List<Endpoint> dashboardList = TransportConfig.getConsoleServerList();
        if (dashboardList.isEmpty()) {
            RecordLog.info("[HttpHeartbeatSender] No dashboard server available");
            consoleProtocol = Protocol.HTTP;
            consoleHost = null;
            consolePort = -1;
        } else {
            consoleProtocol = dashboardList.get(0).getProtocol();
            consoleHost = dashboardList.get(0).getHost();
            consolePort = dashboardList.get(0).getPort();
            RecordLog.info("[HttpHeartbeatSender] Dashboard address parsed: <{}:{}>", consoleHost, consolePort);
        }
        this.client = HttpClientsFactory.getHttpClientsByProtocol(consoleProtocol);
    }

    @Override
    public boolean sendHeartbeat() throws Exception {
        if (StringUtil.isEmpty(consoleHost)) {
            return false;
        }
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(consoleProtocol.getProtocol()).setHost(consoleHost).setPort(consolePort)
                .setPath(TransportConfig.getHeartbeatApiPath())
                .setParameter("app", AppNameUtil.getAppName())
                .setParameter("app_type", String.valueOf(SentinelConfig.getAppType()))
                .setParameter("v", Constants.SENTINEL_VERSION)
                .setParameter("version", String.valueOf(System.currentTimeMillis()))
                .setParameter("hostname", HostNameUtil.getHostName())
                .setParameter("ip", TransportConfig.getHeartbeatClientIp())
                .setParameter("port", String.valueOf(TransportConfig.getPort()))
                .setParameter("pid", String.valueOf(PidUtil.getPid()));

        HttpGet request = new HttpGet(uriBuilder.build());
        request.setConfig(requestConfig);

        // Send heartbeat request using HttpClient 5's execute method
        return client.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode == OK_STATUS) {
                return true;
            } else if (clientErrorCode(statusCode) || serverErrorCode(statusCode)) {
                RecordLog.warn("[HttpHeartbeatSender] Failed to send heartbeat to "
                        + consoleHost + ":" + consolePort + ", http status code: " + statusCode);
            }
            return false;
        });
    }

    @Override
    public long intervalMs() {
        return 5000;
    }

    private boolean clientErrorCode(int code) {
        return code > 399 && code < 500;
    }

    private boolean serverErrorCode(int code) {
        return code > 499 && code < 600;
    }
}