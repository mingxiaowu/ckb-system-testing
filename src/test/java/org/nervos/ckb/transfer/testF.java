package org.nervos.ckb.transfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.testng.annotations.Test;

/**
 * @Author wucj
 * @Date 2019/7/12 下午2:40
 * @Version 1.0
 */


public class testF extends transferMethod {

  String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";

  String minerLockHash = "0xc59af656a387935f7de582e7215b22515742888ead001f32619d5cb95f978855";
  String minerArgs = "0x3954acece65096bfa81258983ddb83915fc56bd8";
  String minerPrivatekey = "0x845b781a1a094057b972714a2b09b85de4fc2eb205351c3e5179aabd264f3805";

  @Test
  public void testA() throws Exception {

    int n = 200;
    int blockNum = 46550;

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

//      outputs.put("0xee8d3f33109b955a3d69f4de187d1ddd1580bd1a", m.toString());//toAddress testA
      outputs.put("0x6ae039579f5164da51f81cc7e78648a3e759f5af", m.toString());//testB
      outputs.put(minerArgs, "10000000000");//miner

      // fromPrivateKey
      CkbTransaction tx = sendTx(txHash, index, blockHash, codeHash, outputs, minerPrivatekey);
    }

  }


  public CkbTransaction sendTx(String txHash, String index, String blockHash, String codeHash,
      Map<String, String> outputs, String toPrivateKey)
      throws Exception {
    String version = "0";

    Transaction tra = new Transaction(version, depences(), sendFrom(txHash, index, blockHash),
        sendTo(codeHash, outputs),
        Collections.singletonList(new Witness(Collections.emptyList())));

    String hash = ckbService.computeTransactionHash(tra).send().getTransactionHash();
    Transaction signedTx = tra.sign(Numeric.toBigInt(toPrivateKey), hash);

    CkbTransactionHash ckbHash = ckbService.sendTransaction(signedTx).send();

    String hash2 = ckbHash.getTransactionHash();
    System.out.println("hash is: " + hash2);

    CkbTransaction aTx = ckbService.getTransaction(hash2).send();

    return aTx;
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


  public String getBlockInfo() throws Exception {
    waitForBlockHeight(BigInteger.valueOf(3), 60, 2);
    String txhash = ckbService.getBlockByNumber("0").send().result.transactions.get(0).hash;
    System.out.println("txhash: " + txhash);
    return txhash;
  }

}



