package org.nervos.ckb.transfer;

import java.util.concurrent.Callable;

/**
 * @Author wucj
 * @Date 2019/7/3 下午6:40
 * @Version 1.0
 */
public class getNum implements Callable<String> {

  private String name;

  private Integer sleepTimes;

  public getNum(String name, Integer sleepTimes) {
    this.name = name;
    this.sleepTimes = sleepTimes;
  }

  public String call() throws Exception {
    // 假设这是一个比较耗时的操作
    Thread.sleep(sleepTimes * 100);
    return "this is content : hello " + this.name;
  }
}
