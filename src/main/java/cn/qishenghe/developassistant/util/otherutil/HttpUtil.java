package cn.qishenghe.developassistant.util.otherutil;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;

/**
 * Http请求工具
 */
public class HttpUtil {

    /**
     * Attribute Group
     */
    private String charset;                             // UTF-8 | US-ASCII | ISO-8859-1
    private HttpClient httpClient;

    /**
     * Construction
     */
    public HttpUtil () {
        this.charset = "UTF-8";
        this.httpClient = HttpClients.createDefault();
    }

    public HttpUtil (String charset) {
        this.charset = charset;
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Function Group
     */

    /**
     * 发送 Post 请求
     * url : 请求地址       map : 请求参数
     */
    public String doPost(String url, Map<String, Object> map) {

        HttpPost httpPost;
        String result = null;
        try {
            httpPost = new HttpPost(url);

            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Entry<String, Object> elem : map.entrySet()) {
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue().toString()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 发送 Get 请求
     * url : 请求地址       map : 请求参数
     */
    public String doGet(String url, Map<String, String> map) {

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (String key : map.keySet()) {
                uriBuilder.addParameter(key, map.get(key));
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            String responseContent = null;
            try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    EntityUtils.consume(entity);
                }
                responseContent = entity == null ? null : EntityUtils.toString(entity, charset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseContent;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Getter & Setter
     */

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 向指定URL发送GET方法的请求
     */
    public static String sendGet(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url + "?" + param;
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header!=null) {
            Iterator<Entry<String, String>> it =header.entrySet().iterator();
            while(it.hasNext()){
                Entry<String, String> entry = it.next();
//                System.out.println(entry.getKey()+":"+entry.getValue());
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : map.keySet()) {
//            System.out.println(key + "--->" + map.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static String sendPost(String url, String param, Map<String, Object> header) throws UnsupportedEncodingException, IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        // 设置通用的请求属性
        if (header!=null) {
            for (Entry<String, Object> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue().toString());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if(out!=null){
            out.close();
        }
        if(in!=null){
            in.close();
        }
        return result;
    }

    public static String post(JSONObject contentType, Map<String, Object> header, String path) {
        String result="";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post=new HttpPost(path);
            for (String headerKey : header.keySet()) {
                post.setHeader(headerKey, header.get(headerKey).toString());
            }
            post.setHeader("Content-Type", "application/json");
//            post.addHeader("X-APP-Id", "pp8t336vCK9");//  这几个是设置header头的
//            post.addHeader("X-APP-Key", "Cn0PboLmab");
//            post.addHeader("X-CTG-Request-Id", "123");
            StringEntity s=new StringEntity(contentType.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);
            HttpResponse httpResponse = client.execute(post);
            InputStream in=httpResponse.getEntity().getContent();
            BufferedReader br=new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber=new StringBuilder();
            String line=null;
            while ((line=br.readLine())!=null) {
                strber.append(line+"\n");

            }
            in.close();
            result=strber.toString();
            if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                result="服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
//        System.out.println("result=="+result);//请求返回
        return result;
    }

    public static String get(Map<String, Object> header, String path) {
        String result="";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet get=new HttpGet(path);
            for (String headerKey : header.keySet()) {
                get.setHeader(headerKey, header.get(headerKey).toString());
            }
//            get.addHeader("X-APP-Id", "pp8t336vCK9");//  这几个是设置header头的
//            get.addHeader("X-APP-Key", "Cn0PboLmab");
//            get.addHeader("X-CTG-Request-Id", "123");
            HttpResponse httpResponse = client.execute(get);
            InputStream in=httpResponse.getEntity().getContent();
            BufferedReader br=new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber=new StringBuilder();
            String line=null;
            while ((line=br.readLine())!=null) {
                strber.append(line+"\n");

            }
            in.close();
            result=strber.toString();
            if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                result="服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
//        System.out.println("result=="+result);//请求返回
        return result;
    }


    public static void main(String[] args) {
        HttpUtil httpUtil = new HttpUtil();
        String url = "https://dd.gtw.pmwangluo.com/DEVICE-SERVICE-MB/hl/permission/login";
        // a_spe_1/qwerasdf
        String username = "a_spe_1";
        String password = "qwerasdf";
//        Map<String, Object> headmap = new HashMap<>();
//
//        Map<String, String> map = new HashMap<>();
//        map.put("username", "admin");
//        map.put("password", "123456");
//        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
//
//        System.out.println(jsonObject);
//        headmap.put("Content-Type", jsonObject);
//        System.out.println(httpUtil.doPost(url, headmap));

        // {"password":"123456","username":"admin"}
        // url : https://dd.gtw.pmwangluo.com/DEVICE-SERVICE-MB/api/res/device/list?protocol=2
        // protocol=2为温湿度设备，0为南网设备，1为国网设备


//        System.out.println(HttpUtil.post(url, "{\"password\":\"123456\",\"username\":\"admin\"}"));
        // 659410575155004382
        // ?startTime=2019-08-16%2016:00:00&endTime=2019-08-31%2016:45:00&deviceId=659410575155004382
        String url2 = "https://dd.gtw.pmwangluo.com/DEVICE-SERVICE-MB/api/data/listDataTemperatureHumidity";

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("dbType", "data");
        jsonMap.put("deviceId", "659410575155004382");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jsonMap);
        System.out.println("jsonObject : " + jsonObject.toString());

        Map<String, Object> header = new HashMap<>();
        header.put("token", "OWYyMTIzM2E4N2QzNThlY2U5M2I2NGM5NTA4N2NiMGMyY3draHN5N2VhN2w3Z2U2MHdvdnBhY2l6aGFqMnF2d2wzajNkZDhudWtpeGg2aGlwdnYxdnZiOGN0MWg4dWF5");

        System.out.println(HttpUtil.post(jsonObject, header, url2));

//        header.put("dbType", "data");
//        header.put("deviceId", "659410575155004382");

//        header.put("Content-Type", "application/json");
//        Map<String, String> contentMap = new HashMap<>();
//        contentMap.put("dbType", "data");
//        contentMap.put("deviceId", "659410575155004382");

        // {"access_token":"OWYyMTIzM2E4N2QzNThlY2U5M2I2NGM5NTA4N2NiMGNicHlkNmJkMW50dGVtMHl4aHFycXB3NXUwdHQzM3FhZ3k0aXE2ZGdheHViMmk1dWJjNzlwdHRnMnFnZmQxbmJn"}

//        System.out.println(HttpUtil.postMap(url2, header, contentMap));


    }

}
