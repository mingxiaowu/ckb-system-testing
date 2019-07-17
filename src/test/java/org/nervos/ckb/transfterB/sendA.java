package org.nervos.ckb.transfterB;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.nervos.ckb.methods.type.transaction.Transaction;
import org.testng.annotations.Test;

/**
 * @Author wucj
 * @Date 2019/7/12 下午5:21
 * @Version 1.0
 */
public class sendA extends methodB {

  String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";

  String minerLockHash = "0xc59af656a387935f7de582e7215b22515742888ead001f32619d5cb95f978855";
  String minerArgs = "0x3954acece65096bfa81258983ddb83915fc56bd8";
  String minerPrivatekey = "0x845b781a1a094057b972714a2b09b85de4fc2eb205351c3e5179aabd264f3805";

  @Test
  public void a() throws Exception {
    ExecutorService executeService = Executors.newFixedThreadPool(100);
    List<tx> taskList = new ArrayList<>();

    int n = 1100;
    int blockNum = 1510;

    for (int i = 0; i < n; i++) {
      int block = blockNum + i;
      //miner send to alcie
      String txHash = getCellByLockHash(minerLockHash, String.valueOf(block), String.valueOf(block), 0).outPoint.cell.txHash;
      String index = "0";
      String blockHash = getCellByLockHash(minerLockHash, String.valueOf(block), String.valueOf(block), 0).outPoint.blockHash;
      String a = getCellByLockHash(minerLockHash, String.valueOf(block), String.valueOf(block), 0).capacity;
      Long m = Long.valueOf(a) - 5000000000L - 10000000000L;

      Map<String, String> outputs = new HashMap<>();

      outputs.put("0x4f6d495fc8fe634e581bd1b3fcdb7d2c1add64c8", m.toString());//testD
      outputs.put(minerArgs, "10000000000");//miner

      Transaction signed = signedTx(txHash, index, blockHash, codeHash, outputs, minerPrivatekey);

      taskList.add(new tx(signed));
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
