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

public class sendTx2 extends JavaSDKTestBase {

  String lockHash = "0xc59af656a387935f7de582e7215b22515742888ead001f32619d5cb95f978855";
//  String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";

  String privateKey="0x845b781a1a094057b972714a2b09b85de4fc2eb205351c3e5179aabd264f3805";

  @Test
  public void testA() throws Exception {

    //start miner
//    String ckbMiner = "ckb miner";
//    ckbSystem.runCommandWithDocker(ckbMiner, "-d -it");
//    Thread.sleep(10000);

    String txHash = getCellByLockHash(lockHash, 0).outPoint.cell.txHash;
    String index = "0";
    String blockHash = getCellByLockHash(lockHash, 0).outPoint.blockHash;
    String capacity = "10000000000";//转账金额
    String aliceArgs = "0x1a72a2489868418b3ab892eadcba281c5185c021";//args of alice

//    String privateKey = "0x845b781a1a094057b972714a2b09b85de4fc2eb205351c3e5179aabd264f3805";

    String args2 = "0x3954acece65096bfa81258983ddb83915fc56bd8";
    String traceCapacity = "88000000000";//找零

    String bobArgs = "0x1a72a2489868418b3ab892eadcba281c5185c021";
    String carArgs = "0xc40ecea46d39d54a9a1dd0bd80d20963632db258";

    Map<String, String> outputs = new HashMap<>();
    outputs.put(aliceArgs, capacity);//转账

    outputs.put("0x3954acece65096bfa81258983ddb83915fc56bd8", traceCapacity);//找零

    CkbTransaction tx = sendTx(txHash, index, blockHash, codeHash, outputs,
        privateKey);//miner.send_capacity(alice.address, capacity)

    Thread.sleep(10000);


//    String txHash1 = getCellByLockHash(lockHash, 1).outPoint.cell.txHash;
//    String blockHash1 = getCellByLockHash(lockHash, 1).outPoint.blockHash;
//
//    Map<String, String> outputs1 = new HashMap<>();
//    outputs1.put(aliceArgs, capacity);//转账
//    outputs1.put(args, traceCapacity);//找零
//
//    CkbTransaction tx1 = sendTx(txHash1, index, blockHash1, codeHash, outputs1,
//        privateKey);//miner.send_capacity(alice.address, capacity)
//
//    Thread.sleep(10000);




//    String aclieLockHash="0x9a8a6b4a3f93a432078da1faa1db6cc248c519fa0c2476d9fdc58a00865f8936";
//    String txHash2 = getCellByLockHash(aclieLockHash, 0).outPoint.cell.txHash;
//    String index2 = "0";
//    String blockHash2 = getCellByLockHash(aclieLockHash, 0).outPoint.blockHash;
//
//    String alicePrivateKey ="0xa66543f1908f73b4d8c6a5b40d33397590bbebcc3e5d482aa2645ea00d9adc35";
//    String bobArgs="0x85b72e2e35b90fc02ad7cae24c6397c0b0f8fd6b";
//    Map<String, String> outputs2 = new HashMap<>();
//    outputs2.put(bobArgs, "6000000000");//转账
//    outputs2.put(aliceArgs, "4000000000");//找零
//
//    CkbTransaction tx2 = sendTx(txHash2, index2, blockHash2, codeHash, outputs2,
//        alicePrivateKey);//alice.send_capacity(bob.address, capacity)


  }

//  @Test
//  public void testB() throws Exception {
//    //start miner
//    String ckbMiner = "ckb miner";
//    ckbSystem.runCommandWithDocker(ckbMiner, "-d -it");
//    Thread.sleep(10000);
//
//    String fromLockHash=lockHash;
//    String fromPrivateKey=privateKey;
//    String aliceArgs = "0x3fa9a748867648783e002803810c38844e5247b4";
//
//    Map<String, String> outputs = new HashMap<>();
//    outputs.put(aliceArgs, "6000000000");//转账
//    outputs.put(args, "4000000000");//找零
//
//    sendAtoB(fromLockHash,fromPrivateKey,outputs);
//  }

  public CkbTransaction sendAtoB(String fromLockHash, String fromPrivateKey, Map<String, String> outputs)
      throws Exception {
    String txHash = getCellByLockHash(fromLockHash, 0).outPoint.cell.txHash;
    String index = "0";
    String blockHash = getCellByLockHash(fromLockHash, 0).outPoint.blockHash;

    CkbTransaction tx = sendTx(txHash, index, blockHash, codeHash, outputs,
        fromPrivateKey);//miner.send_capacity(alice.address, capacity)

    return tx;
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

  public CellOutputWithOutPoint getCellByLockHash(String lockHash, int n) throws Exception {
    long originCapacity;
    Thread.sleep(10000);
    List<CellOutputWithOutPoint> cells = ckbService
        .getCellsByLockHash(lockHash, "774", "775")
        .send()
        .getCells();

    originCapacity = Long.valueOf(cells.get(n).capacity);
    printout("SDK liveCell origin capacity is: " + originCapacity);
    originCapacity = originCapacity / 100000000;
    printout("SDK deal originCapacity is: " + originCapacity);

    return cells.get(n);
  }

  public String getBlockInfo() throws Exception {
    waitForBlockHeight(BigInteger.valueOf(3), 60, 2);
    String txhash = ckbService.getBlockByNumber("0").send().result.transactions.get(0).hash;
    System.out.println("txhash: " + txhash);
    return txhash;
  }

}
