package org.nervos.ckb.explorerTest;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Author wucj
 * @Date 2019/7/5 下午2:23
 * @Version 1.0
 */

/**
 * java发送http的get和post请求
 */
public class HttpRequest {

  //测试发送GET和POST请求
  public static void main(String[] args) throws Exception {
    //发送GET请求
    String s2 = HttpRequest.sendGet("http://47.97.171.140:30001/api/v1/blocks");
    System.out.println(s2);
  }

  /**
   * 发送HttpGet请求
   */
  public static String sendGet(String url) {
    //1.获得一个httpclient对象
    CloseableHttpClient httpclient = HttpClients.createDefault();
    //2.生成一个get请求
    HttpGet httpget = new HttpGet(url);
    httpget.setHeader("Content-Type", "application/vnd.api+json");
    httpget.setHeader("Accept", "application/vnd.api+json");
    CloseableHttpResponse response = null;
    try {
      //3.执行get请求并返回结果
      response = httpclient.execute(httpget);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    String result = null;
    try {
      //4.处理结果，这里将结果返回为字符串
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        result = EntityUtils.toString(entity);
      }
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    } finally {
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * 发送不带参数的HttpPost请求
   * @param url
   * @return
   */
  public static String sendPost(String url) {
    //1.获得一个httpclient对象
    CloseableHttpClient httpclient = HttpClients.createDefault();
    //2.生成一个post请求
    HttpPost httppost = new HttpPost(url);
    CloseableHttpResponse response = null;
    try {
      //3.执行get请求并返回结果
      response = httpclient.execute(httppost);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //4.处理结果，这里将结果返回为字符串
    HttpEntity entity = response.getEntity();
    String result = null;
    try {
      result = EntityUtils.toString(entity);
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return result;
  }


}