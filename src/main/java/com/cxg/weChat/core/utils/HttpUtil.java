package com.cxg.weChat.core.utils;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;


/**
* @Description:    封装http请求
* @Author:         Cheney Master
* @CreateDate:     2018/10/30 11:30
* @Version:        1.0
*/
public class HttpUtil {

    private static final String Charset = "utf-8";
    /**
     * 发送请求，如果失败，会返回null
     * @param url
     * @param map
     * @return
     */
    public static String post(String url, Map<String, String> map) {
        // 处理请求地址
        try {
            HttpClient client = HttpClientBuilder.create().build();
            URI uri = new URI(url);
            HttpPost post = new HttpPost(uri);

            // 添加参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String str : map.keySet()) {
                params.add(new BasicNameValuePair(str, map.get(str)));
            }
            post.setEntity(new UrlEncodedFormEntity(params, Charset));
            // 执行请求
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                // 处理请求结果
                StringBuffer buffer = new StringBuffer();
                InputStream in = null;
                try {
                    in = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,Charset));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 关闭流
                    if (in != null)
                        try {
                            in.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }

                return buffer.toString();
            } else {
                return null;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;

    }

    /**
     * 发送请求，如果失败会返回null
     * @param url
     * @param str
     * @return
     */
    public static String post(String url, String str) {
        // 处理请求地址
        try {
            HttpClient client = HttpClientBuilder.create().build();
            URI uri = new URI(url);
            HttpPost post = new HttpPost(uri);
            post.setEntity(new StringEntity(str, Charset));
            // 执行请求
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                // 处理请求结果
                StringBuffer buffer = new StringBuffer();
                InputStream in = null;
                try {
                    in = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                } finally {
                    // 关闭流
                    if (in != null)
                        in.close();
                }

                return buffer.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 发送GET方式的请求，并返回结果字符串。
     * <br>
     * 时间：2017年2月27日，作者：http://wallimn.iteye.com
     * @param url
     * @return 如果失败，返回为null
     */
    public static String get(String url) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            URI uri = new URI(url);
            HttpGet get = new HttpGet(uri);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                StringBuffer buffer = new StringBuffer();
                InputStream in = null;
                try {
                    in = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,Charset));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                } finally {
                    if (in != null)
                        in.close();
                }

                return buffer.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * @Description https request
     * @Author xg.chen
     * @Date 12:48 2020/3/30
     **/
    public static String httpsRequest(String requestUrl, String data) {
        //请求方法
        String requestMethod = "POST";
        //输出字符串
        StringBuffer buffer = new StringBuffer();
        try {
            //url请求连接
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            //如果是get请求，则直接连接
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            //输入参数为不为空，则处理输参数
            if (null != data) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            //连接成功以后输出参数
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
