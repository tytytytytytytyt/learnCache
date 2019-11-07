package com.geotmt.cacheprime.utils;


import com.geotmt.cacheprime.utils.bean.HttpResult;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Date: 2019/3/30
 * Time: 15:19
 * okHttp工具类
 * 目前使用重用链接设计，这样可以减少http请求的握手时间
 * 默认提供2种默认超时时间 5s，10s，后续会提供不复用链接，指定超时时间的方法
 *
 * @author 夏海华
 */
@Slf4j
public class OkHttpUtil {

    /**
     * 默认client 5s超时
     */
    private static OkHttpClient client1 = null;

    /**
     * client2 10s超时
     */
    private static OkHttpClient client2 = null;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    public static void main(String[] args) {

    }

    /**
     * 5s超时
     * {@link #doGet(String, Map, Map, OkHttpClient)}
     */
    public static HttpResult doGet(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        return doGet(url, headerMap, paramMap, client1);
    }

    /**
     * 10s超时
     * {@link #doGet(String, Map, Map, OkHttpClient)}
     */
    public static HttpResult doGet2(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        return doGet(url, headerMap, paramMap, client2);
    }

    /**
     * form表单方式提交get请求
     */
    private static HttpResult doGet(String url, Map<String, String> headerMap, Map<String, String> paramMap, OkHttpClient client) {
        if (headerMap == null) {
            headerMap = Maps.newHashMap();
        }
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (paramMap != null) {
            for (Map.Entry<String, String> param : paramMap.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        //request
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .headers(Headers.of(headerMap))
                .get()
                .build();
        return getHttpResult(request, client);
    }

    /**
     * 5s超时
     * {@link #doPostForm(String, Map, Map, OkHttpClient, Boolean)}
     */
    public static HttpResult doPostForm(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        return doPostForm(url, headerMap, paramMap, client1, false);
    }

    /**
     * 10s超时
     * {@link #doPostForm(String, Map, Map, OkHttpClient, Boolean)}
     */
    public static HttpResult doPostForm2(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        return doPostForm(url, headerMap, paramMap, client2, false);
    }

    /**
     * 5s超时,指定utf8，正常post form不需要指定utf8 F22数据源需要指定，其他不用改
     */
    public static HttpResult doPostFormUTF8(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        return doPostForm(url, headerMap, paramMap, client1, true);
    }

    /**
     * form表单方式提交post请求
     * @param isUTF8 是否指定UTF8字符编码，默认不指定
     */
    private static HttpResult doPostForm(String url, Map<String, String> headerMap, Map<String, String> paramMap, OkHttpClient client, Boolean isUTF8) {
        if (headerMap == null) {
            headerMap = Maps.newHashMap();
        }
        //构造form body
        RequestBody formBody = getFormBody(paramMap, isUTF8);
        //request
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headerMap))
                .post(formBody)
                .build();
        return getHttpResult(request, client);
    }


    /**
     * 5s超时
     * {@link #doPostBody(String, Map, String, OkHttpClient)}
     */
    public static HttpResult doPostBody(String url, Map<String, String> headerMap, String json) {
        return doPostBody(url, headerMap, json, client1);
    }

    /**
     * 10s超时
     * {@link #doPostBody(String, Map, String, OkHttpClient)}
     */
    public static HttpResult doPostBody2(String url, Map<String, String> headerMap, String json) {
        return doPostBody(url, headerMap, json, client2);
    }

    /**
     * json body提交post请求
     *
     * @param url       url
     * @param headerMap 请求头
     * @param json      请求body
     * @param client    指定的OkHttpClient
     */
    private static HttpResult doPostBody(String url, Map<String, String> headerMap, String json, OkHttpClient client) {
        if (headerMap == null) {
            headerMap = Maps.newHashMap();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headerMap))
                .post(body)
                .build();
        return getHttpResult(request, client);
    }

    private static RequestBody getFormBody(Map<String, String> params, Boolean isUTF8) {
        if (isUTF8 != null && isUTF8) {
            return getFormBodyUTF8(params);
        } else {
            return getFormBody(params);
        }
    }

    /**
     * 根据map构造form表单参数
     */
    private static RequestBody getFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder(StandardCharsets.UTF_8);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 根据map构造form表单参数
     */
    private static RequestBody getFormBodyUTF8(Map<String, String> params) {
        FormBodyUTF8.Builder builder = new FormBodyUTF8.Builder(StandardCharsets.UTF_8);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }


    private static HttpResult getHttpResult(Request request, OkHttpClient client) {
        HttpResult httpResult = new HttpResult();
        try {
            Response response = client.newCall(request).execute();
            httpResult.setHttpCode(response.code());
            if (!response.isSuccessful()) {
                log.error("okHttp response error,request:{}", request.toString());
            }
            if (response.body() != null) {
                httpResult.setResult(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("okHttp post error", e);
        }
        if (Strings.isNotEmpty(httpResult.getResult())) {
            log.info("okHttp调用,url:{},result:{}", request.url(), httpResult.getResult());
        }

        return httpResult;
    }

    static {
        getClient();
    }

    private static void getClient() {
        if (client1 == null) {
            client1 = getClient(true, 5);
        }
        if (client2 == null) {
            client2 = getClient(true, 10);
        }
    }

    /**
     * 初始化client 可以复用的client
     *
     * @param ignoreCertificate true/false 是否忽略https证书，主要对自签名证书的验证
     * @param timeout           超时时间
     * @return OkHttpClient
     */
    private static OkHttpClient getClient(boolean ignoreCertificate, int timeout) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        log.info("Initialising httpUtil with default configuration");
        if (ignoreCertificate) {
            builder = configureToIgnoreCertificate(builder);
        }

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(200);
        dispatcher.setMaxRequestsPerHost(50);

        builder.connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .dispatcher(dispatcher);

        //Other application specific configuration

        return builder.build();
    }

    /**
     * 跳过证书验证
     */
    private static OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
        log.warn("Ignore Ssl Certificate");
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            log.warn("Exception while configuring IgnoreSslCertificate" + e, e);
        }
        return builder;
    }

}
