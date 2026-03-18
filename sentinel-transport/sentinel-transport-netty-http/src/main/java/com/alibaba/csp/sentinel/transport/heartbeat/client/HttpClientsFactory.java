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

    private static final int TIMEOUT_MS = 3000;

    // 创建默认的 ConnectionConfig
    private static final ConnectionConfig DEFAULT_CONNECTION_CONFIG = ConnectionConfig.custom()
            .setConnectTimeout(Timeout.of(TIMEOUT_MS, TimeUnit.MILLISECONDS))
            .build();

    // 创建默认的 SocketConfig
    private static final SocketConfig DEFAULT_SOCKET_CONFIG = SocketConfig.custom()
            .setSoTimeout(Timeout.of(TIMEOUT_MS, TimeUnit.MILLISECONDS))
            .build();

    private static class TlsStrategyInstance {
        private static final DefaultClientTlsStrategy TLS_STRATEGY = createTlsStrategy();

        private static DefaultClientTlsStrategy createTlsStrategy() {
            try {
                // 获取或创建SSLContext
                SSLContext sslContext = SslFactory.getSslConnectionSocketFactory() != null
                        ? SslFactory.getSslConnectionSocketFactory()
                        : SSLContexts.createDefault();

                // 创建 TLS 策略，使用 NoopHostnameVerifier
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
        if (protocol == Protocol.HTTP) {
            // 为HTTP创建带有连接配置的连接管理器
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setDefaultConnectionConfig(DEFAULT_CONNECTION_CONFIG)
                            .setDefaultSocketConfig(DEFAULT_SOCKET_CONFIG)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        } else {
            // 为HTTPS创建带有TLS策略和连接配置的连接管理器
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setTlsSocketStrategy(TlsStrategyInstance.TLS_STRATEGY)
                            .setDefaultConnectionConfig(DEFAULT_CONNECTION_CONFIG)
                            .setDefaultSocketConfig(DEFAULT_SOCKET_CONFIG)
                            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                            .setConnPoolPolicy(PoolReusePolicy.LIFO)
                            .build();

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        }
    }
}