package org.nervos.ckb.transfer;

import java.util.Map;
import java.util.concurrent.Callable;
import org.nervos.ckb.methods.response.CkbTransaction;

/**
 * @Author wucj
 * @Date 2019/7/3 下午7:14
 * @Version 1.0
 */
public class transaction extends transferMethod implements Callable<String> {

  String txHash;
  String index;
  String blockHash;
  String codeHash;
  Map<String, String> outputs;
  String privateKey;

  public transaction(String txHash, String index, String blockHash, String codeHash,
      Map<String, String> outputs, String privateKey) {

    this.txHash = txHash;
    this.index = index;
    this.blockHash = blockHash;
    this.codeHash = codeHash;
    this.outputs = outputs;
    this.privateKey = privateKey;

  }

  public String call() throws Exception {
    // 假设这是一个比较耗时的操作
    CkbTransaction tx = sendTx(txHash, index, blockHash, codeHash, outputs,
        privateKey);
//    tx.result.hashCode();
    return "ok";
  }
}
