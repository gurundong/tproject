package com.gurundong.threadproject.thread.common.config;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        /**
         * 链接配置
         */
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时，链接到目标接口 30秒
        configBuilder.setConnectTimeout(30000);
        // 设置读取超时，等待目标接口超时 60秒
        configBuilder.setSocketTimeout(60000);
        // 设置从连接池获取连接实例的超时，等待连接池可用链接超时 10秒
        configBuilder.setConnectionRequestTimeout(10000);
        // 在提交请求之前 测试连接是否可用
//        configBuilder.setStaleConnectionCheckEnabled(true);
        //cookie管理规范设定，此处有多种可以设置，按需要设置
//        configBuilder.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY);
        RequestConfig requestConfig = configBuilder.build();

        /**
         * 池化定义
         */
        // 默认该实现会为每个路由保持2个并行连接，总的数量上不超过20个连接
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 连接池的最大连接数
        cm.setMaxTotal(200);
        // 每个路由的最大连接数
        cm.setDefaultMaxPerRoute(200);
        // 某个路由的最大连接数
//        HttpHost localhost = new HttpHost("www.baidu.com", 80);
//        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        /**
         * 模拟浏览器cookie，设置到全局httpClient上，每次都发送都可以携带
         */
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("name", "value");
        cookie.setDomain("www.grd.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        /**
         * 配置定义
         */
        // 创建可服用的httpClient
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // httpClientBuilder配置构架器
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        // 设置默认请求配置
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        // 设置默认cookie配置
//        httpClientBuilder.setDefaultCookieStore(cookieStore);
        // 设置默认https配置
//        httpClientBuilder.setSSLSocketFactory(createSSLConn());
//        httpClientBuilder.setDefaultConnectionConfig()
        // 设置默认header配置
//        httpClientBuilder.setDefaultHeaders();


        // 获取httpClient
        CloseableHttpClient build = httpClientBuilder.build();

        // 配置HttpClient的对应工厂HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(build);
        factory.setConnectTimeout(10000);   // 链接超时
        factory.setReadTimeout(10000);
        factory.setConnectionRequestTimeout(1000);
        return factory;
    }

    @Bean
    private static SSLConnectionSocketFactory createSSLConn() {
        SSLConnectionSocketFactory sslsf = null;
        try
        {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext);
        } catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        return sslsf;
    }
}
