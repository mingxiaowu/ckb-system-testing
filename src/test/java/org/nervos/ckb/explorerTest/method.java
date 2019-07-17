package org.nervos.ckb.explorerTest;

import java.util.concurrent.Callable;

/**
 * @Author wucj
 * @Date 2019/7/5 下午3:31
 * @Version 1.0
 */
public class method implements Callable<String> {
  String urlname;

  public method(String urlname){
    this.urlname=urlname;
  }

  @Override
  public String call() {
    //getRequest
    String str =HttpRequest.sendGet(urlname);
    return str;
  }
}
