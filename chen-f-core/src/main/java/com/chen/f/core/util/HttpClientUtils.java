package com.chen.f.core.util;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 发送http请求工具类
 * httpclient 4.5.3
 *
 * @author chen
 * @since 2017/10/27 19:13.
 */
public class HttpClientUtils {
    /**
     * 连接管理器
     */
//    private final static PoolingHttpClientConnectionManager POOLING_HTTP_CLIENT_CONNECTION_MANAGER;
    /**
     * 请求配置
     */
//    private final static RequestConfig REQUEST_CONFIG;
    /**
     * 构建httpclient
     */
    private final static HttpClientBuilder HTTP_CLIENT_BUILDER;

    /**
     * 存储cookie
     */
    private final static BasicCookieStore COOKIE_STORE;
    /**
     * HTTP状态管理上下文
     */
    private final static HttpClientContext HTTP_CLIENT_CONTEXT;
    /**
     * 清除闲置连接
     */
    private final static IdleConnectionEvictor IDLE_CONNECTION_EVICTOR;


    static {
        SSLConnectionSocketFactory sslcsf = null;
        try {
            SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, new TrustStrategy() {
                // 默认信任所有证书
                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            // NoopHostnameVerifier.INSTANCE: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
            sslcsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
            throw new RuntimeException("秘钥管理异常");
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new RuntimeException("关键商店例外");
        }
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslcsf)
                .build();


        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        //最大连接数
        cm.setMaxTotal(200);
        //每个主机地址的并发数
        cm.setDefaultMaxPerRoute(50);
        //检查永久链接的可用性
        cm.setValidateAfterInactivity(2000);

        RequestConfig requestConfig = RequestConfig.custom()
                //创建连接的最长时间
                .setConnectTimeout(30000)
                //从连接池中获取到连接的最长时间
                .setConnectionRequestTimeout(500)
                //数据传输的最长时间
                .setSocketTimeout(200000)
                //设置cookie默认策略
                .setCookieSpec(CookieSpecs.DEFAULT)
//                .setProxy(new HttpHost("47.93.249.153"))
                .build();

        COOKIE_STORE = new BasicCookieStore();
        HTTP_CLIENT_BUILDER = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .setDefaultCookieStore(COOKIE_STORE);

//        创建HTTP状态管理上下文
        HTTP_CLIENT_CONTEXT = HttpClientContext.create();
        HTTP_CLIENT_CONTEXT.setCookieStore(COOKIE_STORE);


        //回收连接
        IDLE_CONNECTION_EVICTOR = new IdleConnectionEvictor(cm, 5, TimeUnit.MINUTES, 1, TimeUnit.MINUTES);
        IDLE_CONNECTION_EVICTOR.start();

    }


    public static void shutdown() {
        HttpClientUtils.IDLE_CONNECTION_EVICTOR.shutdown();
    }


    /**
     * http get请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 响应内容字符串形式
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        return doGet(url, null, paramMap);
    }

    /**
     * http get请求
     *
     * @param url       请求地址
     * @param headerMap header
     * @param paramMap  请求参数
     * @return 响应内容字符串形式
     */
    public static String doGet(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        URI uri = buildUri(url, paramMap);
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        buildHeader(httpGet, headerMap);
        return executeToString(httpGet);
    }


    /**
     * http post请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 响应内容字符串形式
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        return doPost(url, null, paramMap);
    }

    /**
     * http post请求
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param paramMap  请求参数
     * @return 响应内容字符串形式
     */
    public static String doPost(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        buildHeader(httpPost, headerMap);
        buildFormEntity(httpPost, paramMap);
        return executeToString(httpPost);

    }

    /**
     * http post请求  josn请求类型
     *
     * @param url  请求地址
     * @param json json字符串
     * @return 响应内容字符串形式
     */
    public static String doPostJson(String url, String json) {
        return doPostJson(url, null, json);
    }

    /**
     * http post请求  josn请求类型
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param json      json字符串
     * @return 响应内容字符串形式
     */
    public static String doPostJson(String url, Map<String, String> headerMap, String json) {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        buildHeader(httpPost, headerMap);
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        return executeToString(httpPost);
    }


    /**
     * http post请求  formData请求类型
     *
     * @param url         请求地址
     * @param formDataMap formData请求参数
     * @return 响应内容字符串形式
     */
    public static String doPostFormData(String url, Map<String, Object> formDataMap) {
        return doPostFormData(url, null, formDataMap);
    }

    /**
     * http post请求  formData请求类型
     *
     * @param url         请求地址
     * @param headerMap   header参数
     * @param formDataMap formData请求参数
     * @return 响应内容字符串形式
     */
    public static String doPostFormData(String url, Map<String, String> headerMap, Map<String, Object> formDataMap) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setCharset(StandardCharsets.UTF_8);
        if (formDataMap != null && !formDataMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : formDataMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    multipartEntityBuilder.addTextBody(key, (String) value);
                } else if (value instanceof File) {
                    multipartEntityBuilder.addBinaryBody(key, (File) value);
                } else if (value instanceof byte[]) {
                    multipartEntityBuilder.addBinaryBody(key, (byte[]) value);
                } else if (value instanceof InputStream) {
                    multipartEntityBuilder.addBinaryBody(key, (InputStream) value);
                } else {
                    throw new RuntimeException("不支持的formdata类型" + value.getClass());
                }
            }
        }
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        buildHeader(httpPost, headerMap);
        httpPost.setEntity(multipartEntityBuilder.build());
        return executeToString(httpPost);
    }

    /**
     * http put请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 响应内容字符串格式
     */
    public static String doPut(String url, Map<String, String> paramMap) {
        return doPut(url, null, paramMap);
    }

    /**
     * http put请求
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param paramMap  请求参数
     * @return 响应内容字符串格式
     */
    public static String doPut(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        // 创建Http put请求
        HttpPut httpPut = new HttpPut(url);
        buildHeader(httpPut, headerMap);
        buildFormEntity(httpPut, paramMap);
        return executeToString(httpPut);
    }

    /**
     * http put请求  请求类型json
     *
     * @param url  请求地址
     * @param json json字符串
     * @return 响应内容字符串形式
     */
    public static String doPutJson(String url, String json) {
        return doPutJson(url, null, json);
    }

    /**
     * http put请求  请求类型json
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param json      json字符串
     * @return 响应内容字符串形式
     */
    public static String doPutJson(String url, Map<String, String> headerMap, String json) {
        // 创建Http Put请求
        HttpPut httpPut = new HttpPut(url);
        buildHeader(httpPut, headerMap);
        // 创建请求内容
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPut.setEntity(entity);
        return executeToString(httpPut);
    }

    /**
     * http delete请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 响应内容字符串形式
     */
    public static String doDelete(String url, Map<String, String> paramMap) {
        return doDelete(url, null, paramMap);
    }

    /**
     * http delete请求
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param paramMap  请求参数
     * @return 响应内容字符串形式
     */
    public static String doDelete(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
        // 获取Httpclient对象
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        // 创建uri
        URI uri = buildUri(url, paramMap);
        // 创建http delete请求
        HttpDelete httpDelete = new HttpDelete(uri);
        buildHeader(httpDelete, headerMap);
        return executeToString(httpDelete);
    }


    /**
     * http下载
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @param file     保存文件
     */
    public static void doDownload(String url, Map<String, String> paramMap, File file) {
        doDownload(url, null, paramMap, file);
    }

    /**
     * http下载
     *
     * @param url       请求地址
     * @param headerMap header参数
     * @param paramMap  请求参数
     * @param file      保存文件
     */
    public static void doDownload(String url, Map<String, String> headerMap, Map<String, String> paramMap, File file) {
        URI uri = buildUri(url, paramMap);
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        buildHeader(httpGet, headerMap);
        executeToFile(httpGet, file);
    }

    /**
     * 执行请求 并返回Entity
     *
     * @param httpUriRequest 请求对象
     * @param file           保存文件
     */
    private static void executeToFile(HttpUriRequest httpUriRequest, File file) {
        // 获取Httpclient对象
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        // 执行请求
        try (CloseableHttpResponse response = httpClient.execute(httpUriRequest);) {
            HttpEntity entity = response.getEntity();
            try (InputStream content = entity.getContent();) {
                FileUtils.copyInputStreamToFile(content, file);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException("客户端协议异常", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 执行请求 并返回字符串格式响应内容
     *
     * @param httpUriRequest 请求对象
     * @return 响应内容的字符串格式
     */
    private static String executeToString(HttpUriRequest httpUriRequest) {
        // 获取Httpclient对象
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        // 执行请求
        try (CloseableHttpResponse response = httpClient.execute(httpUriRequest);) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException("客户端协议例外", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构建uri
     *
     * @param url      请求url
     * @param paramMap 请求参数
     * @return URI
     */
    private static URI buildUri(String url, Map<String, String> paramMap) {
        // 创建uri
        URI uri = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (paramMap != null && !paramMap.isEmpty()) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("URI语法异常", e);
        }
        return uri;
    }

    /**
     * 构建请求头
     *
     * @param httpRequestBase httpRequestBase
     * @param headerMap       请求头参数
     */
    private static void buildHeader(HttpRequestBase httpRequestBase, Map<String, String> headerMap) {
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach(httpRequestBase::addHeader);
        }
    }

    /**
     * 构建请求体
     *
     * @param requestBase requestBase
     * @param paramMap    请求参数
     */
    private static void buildFormEntity(HttpEntityEnclosingRequestBase requestBase, Map<String, String> paramMap) {
        // 创建参数列表
        if (paramMap != null && !paramMap.isEmpty()) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
            requestBase.setEntity(entity);
        }
    }

}