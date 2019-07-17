package org.nervos.ckb.transfterB;

import java.util.concurrent.Callable;
import org.nervos.ckb.methods.response.CkbTransaction;
import org.nervos.ckb.methods.type.transaction.Transaction;

/**
 * @Author wucj
 * @Date 2019/7/12 下午5:30
 * @Version 1.0
 */
public class tx extends methodB implements Callable<String> {

  Transaction signedTx;

  public tx(Transaction signedTx) {
    this.signedTx = signedTx;

  }

  public String call() throws Exception {
    // 假设这是一个比较耗时的操作
    String hash = sendTx(signedTx);

    return hash;
  }

}
