package org.nervos.ckb.transfterB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.nervos.ckb.javaSDKTest.JavaSDKTestBase;
import org.nervos.ckb.methods.response.CkbTransaction;
import org.nervos.ckb.methods.response.CkbTransactionHash;
import org.nervos.ckb.methods.type.OutPoint;
import org.nervos.ckb.methods.type.Script;
import org.nervos.ckb.methods.type.Witness;
import org.nervos.ckb.methods.type.cell.CellInput;
import org.nervos.ckb.methods.type.cell.CellOutPoint;
import org.nervos.ckb.methods.type.cell.CellOutput;
import org.nervos.ckb.methods.type.cell.CellOutputWithOutPoint;
import org.nervos.ckb.methods.type.transaction.Transaction;
import org.nervos.ckb.utils.Numeric;

/**
 * @Author wucj
 * @Date 2019/7/12 下午5:22
 * @Version 1.0
 */
public class methodB extends JavaSDKTestBase {

  public String sendTx(Transaction signedTx) throws Exception {
    CkbTransactionHash ckbHash = ckbService.sendTransaction(signedTx).send();
//    String hash = ckbHash.getTransactionHash();
//    System.out.println("hash is: " + hash2);
    return ckbHash.getTransactionHash();

  }

  public Transaction signedTx(String txHash, String index, String blockHash, String codeHash,
      Map<String, String> outputs, String toPrivateKey)
      throws Exception {
    String version = "0";

    Transaction tra = new Transaction(version, depences(), sendFrom(txHash, index, blockHash),
        sendTo(codeHash, outputs),
        Collections.singletonList(new Witness(Collections.emptyList())));

    String hash = ckbService.computeTransactionHash(tra).send().getTransactionHash();
    Transaction signedTx = tra.sign(Numeric.toBigInt(toPrivateKey), hash);

    return signedTx;
  }

  //output: to address
  public List<CellOutput> sendTo(String codeHash, Map<String, String> outputs) {
    List<CellOutput> cellOutputs = new ArrayList<>();

    for (String key : outputs.keySet()) {
      String value = outputs.get(key);
      Script lock = new Script(codeHash, Collections.singletonList(key));
      CellOutput cellOutput = new CellOutput(value, "0x", lock);
      cellOutputs.add(cellOutput);
    }

    return cellOutputs;
  }

  //input
  public List<CellInput> sendFrom(String txHash, String index, String blockHash) {
    List<CellInput> cellInputs = new ArrayList<>();
    CellOutPoint cellOutPoint = new CellOutPoint(txHash, index);
    OutPoint previousOutput = new OutPoint(blockHash, cellOutPoint);
    CellInput cellInput = new CellInput(previousOutput, "0");
    cellInputs.add(cellInput);
    return cellInputs;
  }

  public List<OutPoint> depences() throws Exception {
    List<OutPoint> deps = new ArrayList<>();
    CellOutPoint cell0 = new CellOutPoint(
        getBlockInfo(), "1");
    OutPoint dd = new OutPoint(null, cell0);
    deps.add(dd);

    return deps;
  }

  public CellOutputWithOutPoint getCellByLockHash(String lockHash, String fromBlockNum,
      String toBlockNum, int n) throws Exception {
    long originCapacity;
//    Thread.sleep(10000);
    List<CellOutputWithOutPoint> cells = ckbService
        .getCellsByLockHash(lockHash, fromBlockNum, toBlockNum)
        .send()
        .getCells();

    originCapacity = Long.valueOf(cells.get(n).capacity);
    originCapacity = originCapacity / 100000000;
//    printout("SDK deal originCapacity is: " + originCapacity);

    return cells.get(n);
  }

  public String getBlockInfo() throws Exception {
//    waitForBlockHeight(BigInteger.valueOf(3), 60, 2);
    String txhash = ckbService.getBlockByNumber("0").send().result.transactions.get(0).hash;
//    System.out.println("txhash: " + txhash);
    return txhash;
  }


}
