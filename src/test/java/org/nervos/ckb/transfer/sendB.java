package org.nervos.ckb.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.testng.annotations.Test;

/**
 * @Author wucj
 * @Date 2019/7/11 上午10:05
 * @Version 1.0
 */

public class sendB extends transferMethod {

  String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";

  String minerLockHash = "0xc59af656a387935f7de582e7215b22515742888ead001f32619d5cb95f978855";
  String minerArgs = "0x3954acece65096bfa81258983ddb83915fc56bd8";
  String minerPrivatekey = "0x845b781a1a094057b972714a2b09b85de4fc2eb205351c3e5179aabd264f3805";

  @Test
  public void testC() throws Exception {

    ExecutorService executeService = Executors.newCachedThreadPool();
    List<transaction> taskList = new ArrayList<>();
    long startTime = System.currentTimeMillis();

    int n = 20;
    int blockNum = 10800;

//    String args[] = {"0x14587a65fd6b5103637b4bd5bf484bcdd3b217b0",
//        "0x1bc71bfed852ae2fbe456b2f520cde564731d8c7",
//        "0xf32c5c9ddde61f593551993c9eabb576ac842822"};

    for (int i = 0; i < n; i++) {
      int block = blockNum + i;
      //miner send to alcie
      String txHash = getCellByLockHash(minerLockHash, String.valueOf(block),
          String.valueOf(block),
          0).outPoint.cell.txHash;
      String index = "0";
      String blockHash = getCellByLockHash(minerLockHash, String.valueOf(block),
          String.valueOf(block), 0).outPoint.blockHash;
      String a = getCellByLockHash(minerLockHash, String.valueOf(block),
          String.valueOf(block), 0).capacity;
      Long m = Long.valueOf(a) - 5000000000L - 10000000000L;

      Map<String, String> outputs = new HashMap<>();

//      outputs.put(args[i], "40000000000");//toAddress
      outputs.put("0xee8d3f33109b955a3d69f4de187d1ddd1580bd1a", m.toString());//toAddress testA
      outputs.put(minerArgs, "10000000000");//miner

      // fromPrivateKey
      taskList.add(new transaction(txHash, index, blockHash, codeHash, outputs, minerPrivatekey));

    }

    try {
      System.out.println("主线程发起异步任务请求");
      List<Future<String>> resultList = executeService.invokeAll(taskList);

      System.out.println("数量:" + resultList.size());
      // 这里会阻塞等待resultList获取到所有异步执行的结果才会执行
      for (Future<String> future : resultList) {
        System.out.println(future.get());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
