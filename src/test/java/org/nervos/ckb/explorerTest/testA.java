package org.nervos.ckb.explorerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.testng.annotations.Test;

/**
 * @Author wucj
 * @Date 2019/7/5 下午2:08
 * @Version 1.0
 */
public class testA {

  // dev
  String height = "http://47.97.171.140:30001/api/v1/blocks/20";
  String statistics = "http://47.97.171.140:30001/api/v1/statistics";
  String blockhash = "http://47.97.171.140:30001/api/v1/blocks/0xd5c54b0c699ef009edfedb00d8fb8198ac5ce1e9e266b9110a8972e1b05aba88";

  //staging
  String stagingUrl = "https://explorer.staging.nervos.org:30001/api/v1/blocks/1";
  ExecutorService executeService = Executors.newCachedThreadPool();

  int n = 10;
  int m = 50;

  @Test
  public void test1() {
    List<method> taskList = new ArrayList<>();
    for (int i = 1; i < n; i++) {
      taskList.add(new method(height));
    }
    long sum = 0;
    for (int i = 1; i < m; i++) {
      long startTime = System.currentTimeMillis();
      try {
        List<Future<String>> resultList = executeService.invokeAll(taskList);

        for (Future<String> future : resultList) {

          System.out.println(future.get());
        }
        long endTime = System.currentTimeMillis();
        long t = endTime - startTime;
        sum = sum + t;
        System.out.println("第" + i + "次耗时 : " + (t));
      } catch (Exception e) {
        e.printStackTrace();
      }
//    finally {
//      executeService.shutdown();
//    }
    }

    System.out.println("平均: " + sum / m);

  }

}
