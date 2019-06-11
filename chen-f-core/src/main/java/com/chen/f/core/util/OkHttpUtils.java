package com.chen.f.core.util;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @since 2017/12/28 15:03.
 */
public class OkHttpUtils {

    private static final OkHttpClient OK_HTTP_CLIENT;
    private static final ConnectionPool CONNECTION_POOL;
    private static final CookieJar COOKIE_JAR;
    private static final X509TrustManager TRUST_MANAGER;
    private static final SSLSocketFactory SSL_SOCKET_FACTORY;
    private static final HostnameVerifier HOST_NAME_VERIFIER;

    static class OkHttpConfig {
        static X509TrustManager generateX509TrustManager() {
            return new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        }

        static SSLSocketFactory generateSSLSocketFactory(X509TrustManager trustManager) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
                return sslContext.getSocketFactory();
            } catch (KeyManagementException e) {
                e.printStackTrace();
                throw new RuntimeException("秘钥管理异常",e);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException("没有这个算法",e);
            }


//            try {
//                FileInputStream certificate = new FileInputStream(new File(""));
//                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                keyStore.load(null);
//                keyStore.setCertificateEntry("1", certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                trustManagerFactory.init(keyStore);
//                sslContext.init(null,
//                                trustManagerFactory.getTrustManagers(),
//                                new SecureRandom()
//                        );
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return sslContext.getSocketFactory();
        }

        static HostnameVerifier generateHostnameVerifier() {
            return new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };
        }

        static CookieJar generateCookieJar() {
            return new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>(100);

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<>();
                }
            };
        }
    }

    static {
        // 进行初始化操作
        CONNECTION_POOL = new ConnectionPool(5, 10, TimeUnit.MINUTES);
        COOKIE_JAR = OkHttpConfig.generateCookieJar();

        TRUST_MANAGER = OkHttpConfig.generateX509TrustManager();
        SSL_SOCKET_FACTORY = OkHttpConfig.generateSSLSocketFactory(TRUST_MANAGER);
        HOST_NAME_VERIFIER = OkHttpConfig.generateHostnameVerifier();

        OK_HTTP_CLIENT = new OkHttpClient.Builder()
                //设置连接超时
                .connectTimeout(600, TimeUnit.SECONDS)
                //设置读超时
                .readTimeout(600, TimeUnit.SECONDS)
                //设置写超时
                .writeTimeout(600, TimeUnit.SECONDS)
                //是否自动重连
                .retryOnConnectionFailure(false)
                //是否自动重定向
                .followRedirects(false)
                //连接池
                .connectionPool(CONNECTION_POOL)
                //缓存
//                .cache(new Cache(null, 1))
                //cookie自动管理
                .cookieJar(OkHttpConfig.generateCookieJar())
                //忽略不验证所有https  sslSocketFactory可能为空
                .sslSocketFactory(SSL_SOCKET_FACTORY, TRUST_MANAGER)
                .hostnameVerifier(HOST_NAME_VERIFIER)
                .build();


    }

    public static void destroy() {
        if (OK_HTTP_CLIENT != null) {
            ConnectionPool connectionPool = OK_HTTP_CLIENT.connectionPool();
            connectionPool.evictAll();
            ExecutorService executorService = OK_HTTP_CLIENT.dispatcher().executorService();
            executorService.shutdown();
            try {
                executorService.awaitTermination(3, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * http get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应内容字符串形式
     */
    public static String get(final String url, final Map<String, String> params) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new RuntimeException("url格式不正确");
        }

        //处理参数
        if (params != null) {
            HttpUrl.Builder builder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addEncodedQueryParameter(entry.getKey(), entry.getValue());
            }
            httpUrl = builder.build();
        }

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();


        return OkHttpUtils.executeToString(request);
    }


    /**
     * http post请求
     *
     * @param url    请求地址
     * @param params 请求内容
     * @return 响应内容字符串形式
     */
    public static String post(final String url, final Map<String, String> params) {
        //处理参数
        RequestBody requestBody = null;
        if (params != null) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addEncoded(entry.getKey(), entry.getValue());
                //builder.add(entry.getKey(), entry.getValue());
            }
            requestBody = builder.build();
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                //.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                //.header("Content-Type","application/x-www-form-urlencoded")
                //.header("User-Agent","Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043409 Safari/537.36 V1ANDSQ7.2.5744YYBD QQ/7.2.5.3305 NetType/WIFI WebP/0.3.0 Pixel/1080")
                .build();

        return OkHttpUtils.executeToString(request);

    }

    /**
     * http post请求  josn请求类型
     *
     * @param url  请求地址
     * @param json json字符串
     * @return 响应内容字符串形式
     */
    public static String postJson(final String url, final String json) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();

        return OkHttpUtils.executeToString(request);
    }

    public static Response download(final String url, final Map<String, String> params) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new RuntimeException("url格式不正确");
        }

        //处理参数
        if (params != null) {
            HttpUrl.Builder builder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addEncodedQueryParameter(entry.getKey(), entry.getValue());
            }
            httpUrl = builder.build();
        }

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        return OkHttpUtils.executeToResponse(request);
    }


    /**
     * 执行请求 并返回字符串格式响应内容
     *
     * @param request http请求
     * @return 响应内容的字符串格式
     */
    private static Response executeToResponse(Request request) {
        try {
            return OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("okHttp请求异常",e);
        }


    }

    /**
     * 执行请求 并返回字符串格式响应内容
     *
     * @param request http请求
     * @return 响应内容的字符串格式
     */
    private static String executeToString(Request request) {
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                return body == null ? null : body.string();
            } else {
//                throw new RuntimeException("http状态码不是200~300");
                System.out.println("http状态码不是200~300");
                ResponseBody body = response.body();
                return body == null ? null : body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("okHttp请求异常",e);
        }

    }


}
