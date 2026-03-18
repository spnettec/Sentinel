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
package com.alibaba.csp.sentinel.transport.heartbeat.client;

import com.alibaba.csp.sentinel.transport.endpoint.Protocol;
import com.alibaba.csp.sentinel.transport.ssl.SslFactory;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;

import javax.net.ssl.SSLContext;
import java.util.concurrent.TimeUnit;

/**
 * @author Leo Li
 */
public class HttpClientsFactory {

    private static final int DEFAULT_TIMEOUT_MS = 10000;

    private static class TlsStrategyInstance {
        private static final DefaultClientTlsStrategy TLS_STRATEGY = createTlsStrategy();

        private static DefaultClientTlsStrategy createTlsStrategy() {
            try {
                SSLContext sslContext = SslFactory.getSslConnectionSocketFactory();
                if (sslContext == null) {
                    sslContext = SSLContexts.createDefault();
                }

                return new DefaultClientTlsStrategy(
                        sslContext,
                        NoopHostnameVerifier.INSTANCE
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to create DefaultClientTlsStrategy", e);
            }
        }
    }

    public static CloseableHttpClient getHttpClientsByProtocol(Protocol protocol) {
        // 创建基础连接配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS))
                .build();

        // 创建 Socket 配置
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.of(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS))
                .build();

        if (protocol == Protocol.HTTP) {
            // HTTP 客户端配置
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setDefaultConnectionConfig(connectionConfig)
                            .setDefaultSocketConfig(socketConfig)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        } else {
            // HTTPS 客户端配置（包含 TLS 策略）
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setTlsSocketStrategy(TlsStrategyInstance.TLS_STRATEGY)
                            .setDefaultConnectionConfig(connectionConfig)
                            .setDefaultSocketConfig(socketConfig)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        }
    }

    /**
     * 带自定义超时时间的重载方法
     *
     * @param protocol  协议类型
     * @param timeoutMs 超时时间（毫秒）
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClientsByProtocol(Protocol protocol, int timeoutMs) {
        // 创建自定义超时的连接配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(timeoutMs, TimeUnit.MILLISECONDS))
                .build();

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.of(timeoutMs, TimeUnit.MILLISECONDS))
                .build();

        if (protocol == Protocol.HTTP) {
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setDefaultConnectionConfig(connectionConfig)
                            .setDefaultSocketConfig(socketConfig)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        } else {
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setTlsSocketStrategy(TlsStrategyInstance.TLS_STRATEGY)
                            .setDefaultConnectionConfig(connectionConfig)
                            .setDefaultSocketConfig(socketConfig)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        }
    }
}