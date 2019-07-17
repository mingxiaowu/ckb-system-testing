package org.nervos.ckb.wakuang;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.nervos.ckb.TestBase;
import org.nervos.ckb.methods.response.CkbBlock;
import org.nervos.ckb.util.items.Script;
import org.testng.annotations.Test;
import sun.java2d.xr.MutableInteger;

/**
 * @Author wucj
 * @Date 2019/7/15 上午11:22
 * @Version 1.0
 */
public class rankingTest extends TestBase {

  @Test
  public void blockRewardTest() throws IOException {

    String blockNum = ckbService.getTipBlockNumber().send().getBlockNumber().toString();
    int start = Integer.valueOf(blockNum);

    CkbBlock blockInfo;

    HashMap<String, Map<String, String>> counter = new HashMap<>();
    for (int i = 1; i < start; i++) {

      blockInfo = ckbService.getBlockByNumber(String.valueOf(i)).send();

      String args = blockInfo.result.transactions.get(0).outputs.get(0).lock.args.get(0);
      String cap = blockInfo.result.transactions.get(0).outputs.get(0).capacity;

      HashMap<String, String> detail = new HashMap<>();

      MutableInteger initValue = new MutableInteger(1);

      if (counter.containsKey(args)) {

        BigInteger oldCap = new BigInteger(counter.get(args).get("capacity"));
        MutableInteger oldCount = new MutableInteger(
            Integer.valueOf(counter.get(args).get("count")));

        detail.put("count", String.valueOf(oldCount.getValue() + 1));
        detail.put("capacity", oldCap.add(new BigInteger(cap)).toString());//

        counter.put(args, detail);
      } else {
        detail.put("count", String.valueOf(initValue.getValue()));
        detail.put("capacity", cap);
        counter.put(args, detail);
      }

      if (start > 32000) {
        start = Integer.valueOf(ckbService.getTipBlockNumber().send().getBlockNumber().toString());
      }

    }

    System.out.println("参与人数：" + counter.size());

    Long sum = 0l;
//    System.out.println("通过Map.keySet遍历key和value：");
    for (String key : counter.keySet()) {
      String lockHash = lochHash(codeHash, key);

      String cap = counter.get(key).get("capacity");
      long a = Long.valueOf(cap) / 100000000;
      if (a > 4000) {
        sum = sum + Long.valueOf(cap);
        System.out.println(
            "args= " + key + " lockhash=" + lockHash + " and capacity= " + cap + " count="
                + counter.get(key).get("count"));
      }
    }

    System.out.println("sum: " + sum);

  }

  @Test
  public void epchoTest() throws IOException {

    String blockNum = ckbService.getTipBlockNumber().send().getBlockNumber().toString();
    int start = Integer.valueOf(blockNum);
    CkbBlock blockInfo;
    String t = "0";
    int num = 1;
    for (int i = 1; i < start; i++) {

//      if (num > 80) {
//        break;
//      }
      blockInfo = ckbService.getBlockByNumber(String.valueOf(i)).send();
      String epoch = blockInfo.result.header.epoch;

      if (!(t.equals(epoch))) {
        String hash = blockInfo.result.transactions.get(0).outputs.get(0).lock.codeHash;
        String args = blockInfo.result.transactions.get(0).outputs.get(0).lock.args.get(0);

        String lockHash = lochHash(hash, args);
        System.out.println(
            "blockNum:" + i + " ,epoch:" + epoch + " ,lockHash:" + lockHash + " ,args:" + args);

        t = epoch;
        num++;
      }

      if (start > 32000) {
        start = Integer.valueOf(ckbService.getTipBlockNumber().send().getBlockNumber().toString());
      }


    }
    System.out.println("block:" + start);
  }

  @Test
  public void A() throws IOException {

    String blockNum = ckbService.getTipBlockNumber().send().getBlockNumber().toString();
    int sum = 0;
    int start = Integer.valueOf(blockNum);

    CkbBlock blockInfo;

    ArrayList<String> argsList = new ArrayList();

    HashMap<String, MutableInteger> efficientCounter = new HashMap<>();
    String hash;
    for (int i = 1; i < start; i++) {
//    for (int i = start; i > start - 20000; i--) {
      blockInfo = ckbService.getBlockByNumber(String.valueOf(i)).send();

      String args = blockInfo.result.transactions.get(0).outputs.get(0).lock.args.get(0);
      hash = blockInfo.result.transactions.get(0).outputs.get(0).lock.codeHash;
      argsList.add(args);

      MutableInteger initValue = new MutableInteger(1);
      MutableInteger oldValue = efficientCounter.put(args, initValue);

      if (oldValue != null) {
        initValue.setValue(oldValue.getValue() + 1);
      }
    }

    System.out.println("参与人数：" + efficientCounter.size());

    System.out.println("通过Map.keySet遍历key和value：");
    for (String key : efficientCounter.keySet()) {
      String lockHash = lochHash(
          "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933", key);
      System.out.println(
          "args= " + key + " and count= " + efficientCounter.get(key).getValue() + " lockHash:"
              + lockHash);

    }

  }

  @Test
  public void B() throws IOException {

    String blockNum = ckbService.getTipBlockNumber().send().getBlockNumber().toString();
    int start = Integer.valueOf(blockNum);

    CkbBlock blockInfo;

    HashMap<String, BigInteger> counter = new HashMap<String, BigInteger>();
//    HashMap<String, Map<String, String>> counter = new HashMap<>();
    for (int i = 1; i < start; i++) {

      blockInfo = ckbService.getBlockByNumber(String.valueOf(i)).send();

      String args = blockInfo.result.transactions.get(0).outputs.get(0).lock.args.get(0);
      String cap = blockInfo.result.transactions.get(0).outputs.get(0).capacity;

      if (counter.containsKey(args)) {
        MutableInteger initValue = new MutableInteger(1);
        BigInteger oldValue = counter.get(args);
        counter.put(args, oldValue.add(new BigInteger(cap)));
      } else {
        counter.put(args, new BigInteger(cap));
      }

    }

    System.out.println("参与人数：" + counter.size());

//    System.out.println("通过Map.keySet遍历key和value：");
    for (String key : counter.keySet()) {
      System.out.println("args= " + key + " and capacity= " + counter.get(key).longValue());
    }

  }

  public String lochHash(String configCodeHash, String configArgs) {

//    configCodeHash = "0x9e3b3557f11b2b3532ce352bfe8017e9fd11d154c4c7f9b7aaaa1e621b539a08";
//    configArgs = "0x56ba03316484b3718fdda50558600773d389cc8f";

    Script script = new Script(configCodeHash, Collections.singletonList(configArgs));
    String lockHash = script.getLockHash();

    return lockHash;
  }
}
