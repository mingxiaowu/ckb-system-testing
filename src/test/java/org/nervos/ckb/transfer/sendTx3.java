package org.nervos.ckb.transfer;

import java.io.IOException;
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
 * @Date 2019/7/3 下午6:23
 * @Version 1.0
 */
public class sendTx3 extends transferMethod {
  String codeHash="0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";

  String alicePrivateKey="0x41d51ab9e60290c78ef940dd074d960bd83a4435d4b2616e4ad2c7e7b7684d2d";
  String lockHash="0x019d27eb23a43cff75a7ab625eddc377771b2d615c490d07e67916f0fe640000";
  String boblockHash="0x6c15dcf98c0f3cb5ebe53674d04f663468692c72dfa029030e0c586e534f3c8c";//bob
  String chanellockHash="0xf402b0ed27a60b16d63d7e8d2c46a2e759aac0e70586cc35ab82ec0c6e7ad7fa";//chanel
  String bobPrivateKey="0xe8be8ebe037d98ad0198c2af8f82cac43e50eb22e4ccd5356ac71daca5dec7d6";
  String chanelPrivateKey="0x408873e4c88a3c8ba63c4a83f306997655593421f3d0d59252fa3c5e547f6cf8";

  String diorLockhash="0xae289978e2d9941fc312baedd3b03b78f1fc602b0334a98e40ebddf387ea3629";
  String diorPrivateKey="0xd644982032d86de9f55f4cf3b97efee02ee308898110c9030b0b234b1a3144e0";
  @Test
  public void testC() throws Exception {

//    String ckbMiner = "ckb miner";
//    ckbSystem.runCommandWithDocker(ckbMiner, "-d -it");
//    Thread.sleep(10000);

    ExecutorService executeService = Executors.newCachedThreadPool();
    List<transaction> taskList = new ArrayList<>();
    long startTime = System.currentTimeMillis();

    //bob send to alcie
    String txHash = getCellByLockHash(diorLockhash,"15812","15813", 0).outPoint.cell.txHash;
    String index = "0";
    String blockHash = getCellByLockHash(diorLockhash, "15812","15813",0).outPoint.blockHash;
//    String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";
    Map<String, String> outputs = new HashMap<>();

    outputs.put("0x1e526195a71322387d4d961d419a08fd5078a1c9", "6000000000");
    outputs.put("0x42f16c60130b892e6ce3037f86ce37b6341ee635", "6000000000");
    outputs.put("0xae92ffa87ff3325ea3e2a2020179ba8bf877399f", "6000000000");
    outputs.put("0xc13679b3781f5ff896d2aa5017e62aedaf432a46", "6000000000");
    outputs.put("0x8c6585a7851ba42212fbccff4f46a4ae50f8d4b9", "6000000000");
    outputs.put("0x2dec446add65e88eb849ecd2e5230faceb21efb8", "6000000000");
    outputs.put("0x32d7b273cc8e21b0ca38862d0f9f7a21658142bc", "6000000000");
    outputs.put("0x227835fbdedecce0622513de18520cd486285619", "6000000000");
    outputs.put("0x0bfd10cddf00b990f9ef2d458365e4982cbc02d1", "6000000000");
    outputs.put("0xf292d2c4587faa89e9454aa8968a3027666e4863", "6000000000");
    outputs.put("0x7e0e1d06d595250ee4c20604dbc2ca176c0e357b", "6000000000");
    outputs.put("0x52cc7be2598562f843ef3d2c68caebda95255ce0", "6000000000");
    outputs.put("0x22d8fd644834c0f3eae484b42144a77e047a9545", "6000000000");
    outputs.put("0x3b0c93180eee478eb9ad552c26e1201b646f9314", "6000000000");
    outputs.put("0x03e642770badfe78050f28d960e562f3915bf301", "6000000000");
    outputs.put("0x452e4054de32a8537ae626be4937118367489219", "6000000000");
    outputs.put("0xa4ec85891b8a8643542d12fb5f086711f88c22f7", "6000000000");
    outputs.put("0x146898cb85c68e45069979db7df08d5f899607d0", "6000000000");
    outputs.put("0x318f938015d9bc135cdd7d60e276cb0bafb6856c", "6000000000");
    outputs.put("0x4e14e20290a4173c422cc2d29baa1a4a7772d24b", "6000000000");
    outputs.put("0x393bec546e695f2f03c0eccbe8750a07201002cb", "6000000000");
    outputs.put("0xfab4c7a1d8c562c1036a5c44312c3d96bee4134f", "6000000000");


//    String privateKey = "";
    taskList.add(new transaction(txHash, index, blockHash, codeHash, outputs, diorPrivateKey));


    //chanel send to bob
//    String txHash2 = getCellByLockHash(chanellockHash, "3074","3075",0).outPoint.cell.txHash;
//    String index2 = "2";
//    String blockHash2 = getCellByLockHash(chanellockHash, "3074","3075",0).outPoint.blockHash;
//    Map<String, String> outputs2 = new HashMap<>();
//    outputs2.put("0xf75b84fb2d9c51e34068e0c6ae92ffbcf70deec3", "10000000000");//alice
//    outputs2.put("0xc9c09aa5fc274307fa5d7312dcb7fa960842b47c", "50000000000");//bob
//    outputs2.put("0xfb5a57eec8649655e4dcfd2fb0084a323389c3a8", "28000000000");//owner
//    taskList.add(new transaction(txHash2, index2, blockHash2, codeHash, outputs2, chanelPrivateKey));

    try {
      System.out.println("主线程发起异步任务请求");
      List<Future<String>> resultList = executeService.invokeAll(taskList);

      // 这里会阻塞等待resultList获取到所有异步执行的结果才会执行
      for (Future<String> future : resultList) {
        System.out.println(future.get());
      }
      long endTime = System.currentTimeMillis();
      System.out.println("耗时 : " + (endTime - startTime) / 1000);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
