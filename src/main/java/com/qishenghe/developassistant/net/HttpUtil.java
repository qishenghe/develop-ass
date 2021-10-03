package com.qishenghe.developassistant.net;

import com.qishenghe.developassistant.exception.DevelopAssException;
import com.qishenghe.developassistant.io.FileUtil;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Http工具
 *
 * @author qishenghe
 * @date 2021/10/3 18:50
 * @change 2021/10/3 18:50 by qishenghe for init
 */
@Data
public class HttpUtil {

    /**
     * 默认编码字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 编码字符集
     */
    private Charset charset;

    /**
     * HttpClient
     */
    private HttpClient httpClient;

    /**
     * 无参构造函数
     */
    public HttpUtil() {
        this.charset = Charset.forName(DEFAULT_CHARSET_NAME);
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * 构造函数
     *
     * @param charset 编码字符集
     */
    public HttpUtil(Charset charset) {
        this.charset = charset;
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * 构造函数
     *
     * @param charset    编码字符集
     * @param httpClient http客户端
     */
    public HttpUtil(Charset charset, HttpClient httpClient) {
        this.charset = charset;
        this.httpClient = httpClient;
    }

    /**
     * Get请求
     *
     * @param uriBuilder uriBuilder
     * @param headerList HeaderList
     * @return 请求结果
     * @author qishenghe
     * @date 2021/10/3 19:46
     * @change 2021/10/3 19:46 by qishenghe for init
     * @since 1.0.0
     */
    public String get(URIBuilder uriBuilder, List<Header> headerList) {
        String result;
        try {
            // Get
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // send
            result = send(httpGet, headerList);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new DevelopAssException(FileUtil.class.getName() + " : cause : " + "url构建异常");
        }
        return result;
    }

    /**
     * 【重载】Get请求
     *
     * @param host       地址
     * @param port       端口
     * @param path       路径
     * @param paramList  参数列表
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String get(String host, int port, String path, List<NameValuePair> paramList, List<Header> headerList) {
        return get(buildUri(host, port, path, paramList), headerList);
    }

    /**
     * 【重载】Get请求
     *
     * @param url        url
     * @param paramList  参数列表
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String get(String url, List<NameValuePair> paramList, List<Header> headerList) {
        return get(buildUri(url, paramList), headerList);
    }

    /**
     * 【重载】Get请求
     *
     * @param url        url
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String get(String url, List<Header> headerList) {
        return get(buildUri(url), headerList);
    }

    /**
     * Post请求
     *
     * @param uriBuilder uriBuilder
     * @param headerList headerList
     * @return 请求结果
     * @author qishenghe
     * @date 2021/10/3 20:17
     * @change 2021/10/3 20:17 by qishenghe for init
     * @since 1.0.0
     */
    public String post(URIBuilder uriBuilder, List<Header> headerList) {
        String result;
        try {
            // Post
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            // send
            result = send(httpPost, headerList);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new DevelopAssException(FileUtil.class.getName() + " : cause : " + "url构建异常");
        }
        return result;
    }

    /**
     * 【重载】Post请求
     *
     * @param host       地址
     * @param port       端口
     * @param path       路径
     * @param paramList  参数列表
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String post(String host, int port, String path, List<NameValuePair> paramList, List<Header> headerList) {
        return post(buildUri(host, port, path, paramList), headerList);
    }

    /**
     * 【重载】Post请求
     *
     * @param url        url
     * @param paramList  参数列表
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String post(String url, List<NameValuePair> paramList, List<Header> headerList) {
        return post(buildUri(url, paramList), headerList);
    }

    /**
     * 【重载】Post请求
     *
     * @param url        url
     * @param headerList HeaderList
     * @return 请求结果
     */
    public String post(String url, List<Header> headerList) {
        return post(buildUri(url), headerList);
    }

    /**
     * 【封装】构建Uri生成器
     *
     * @param host      地址
     * @param port      端口
     * @param path      路径
     * @param paramList 参数列表
     * @return URIBuilder
     */
    private URIBuilder buildUri(String host, int port, String path, List<NameValuePair> paramList) {

        // 构建Url
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost(host);
        uriBuilder.setPort(port);
        uriBuilder.setPath(path);
        uriBuilder.setCharset(this.charset);
        if (!CollectionUtils.isEmpty(paramList)) {
            // 追入参数列表
            uriBuilder.setParameters(paramList);
        }

        return uriBuilder;
    }

    /**
     * 【封装】【重载】构建Uri生成器
     *
     * @param url       url
     * @param paramList 参数列表
     * @return URIBuilder
     */
    private URIBuilder buildUri(String url, List<NameValuePair> paramList) {

        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(url);

            uriBuilder.setCharset(this.charset);
            if (!CollectionUtils.isEmpty(paramList)) {
                uriBuilder.setParameters(paramList);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new DevelopAssException(FileUtil.class.getName() + " : cause : " + "自定义url解析异常");
        }

        return uriBuilder;
    }

    /**
     * 【封装】【重载】构建Uri生成器
     *
     * @param url url
     * @return URIBuilder
     */
    private URIBuilder buildUri(String url) {
        return buildUri(url, null);
    }

    /**
     * 【封装】封装send
     *
     * @param httpRequestBase httpRequestBase
     * @param headerList      headerList
     * @return 请求结果
     */
    private String send(HttpRequestBase httpRequestBase, List<Header> headerList) {
        String result;
        try {
            // 追入Header
            if (!CollectionUtils.isEmpty(headerList)) {
                httpRequestBase.setHeaders(headerList.toArray(new Header[0]));
            }
            // 执行请求
            HttpResponse response = this.httpClient.execute(httpRequestBase);
            // 返回结果
            result = EntityUtils.toString(response.getEntity(), this.charset);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DevelopAssException(FileUtil.class.getName() + " : cause : " + "Http请求执行异常");
        }
        return result;
    }


}
