//package org.nervos.ckb.transfer;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import org.nervos.ckb.javaSDKTest.JavaSDKTestBase;
//import org.nervos.ckb.methods.response.CkbTransaction;
//import org.nervos.ckb.methods.type.CellInput;
//import org.nervos.ckb.methods.type.CellOutPoint;
//import org.nervos.ckb.methods.type.CellOutput;
//import org.nervos.ckb.methods.type.CellOutputWithOutPoint;
//import org.nervos.ckb.methods.type.OutPoint;
//import org.nervos.ckb.methods.type.Script;
//import org.nervos.ckb.methods.type.Transaction;
//import org.nervos.ckb.methods.type.Witness;
////import org.nervos.ckb.util.items.Script;
//import org.nervos.ckb.utils.Numeric;
//import org.testng.annotations.Test;
//
///**
// * @Author wucj
// * @Date 2019/6/21 下午3:55
// * @Version 1.0
// */
//public class sendTx extends JavaSDKTestBase {
//  /**
//   * test1.转账 >= 60 ckb，交易成功
//   * test2.转账 < 60 ckb，交易失败
//   *
//   * test3.address中 有多个cell，转账后output中cellbase < 60，交易成功，切会与其他的cell合并成一个新的，不会单独生成
//   * test4.address中 只有一个cell，转账后output中cellbase < 60，交易会失败
//   *
//   */
//
//  /**
//   * test1: 1.启动节点 2。Alice挖矿 3。Alice 转账 Bob 60ckb/100ckb 4。根据txhash 查询交易详情 5。get_balance(Bob)
//   * ,Bob.balance=60/100 ckb
//   */
//
////  private String bobCodeHash = codeHash;
////  private String bobArgs = "0x36c329ed630d6ce750712a477543672adab57f4c";
////  private String bobAddr = "ckt1q9gry5zgxmpjnmtrp4kww5r39frh2sm89tdt2l6v234ygf";
//  String lockHash="0xc59af656a387935f7de582e7215b22515742888ead001f32619d5cb95f978855";
//  @Test
//  public void testA() throws Exception {
//
//
//    //start miner
////    String ckbMiner = "ckb miner";
////    ckbSystem.runCommandWithDocker(ckbMiner, "-d -it");
////    Thread.sleep(10000);
//
//    String txHash = getOriginCapacity(lockHash, 0).outPoint.cell.txHash;
//    String index = "0";
//    String blockHash = getOriginCapacity(lockHash, 0).outPoint.blockHash;
//    String capacity = "990000000000";
//    String codeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";
//    String aliceArgs = "0x1a72a2489868418b3ab892eadcba281c5185c021";//args of alice
//    Script minerLock = new Script(codeHash, Collections.singletonList(args));
//    Long traceCapacity =
//        Long.valueOf(getOriginCapacity(lockHash, 0).capacity) - Long.valueOf(capacity);
//
//    CkbTransaction tx = sendTx(txHash, index, blockHash, capacity, codeHash, aliceArgs,
//        privateKey);//miner.send_capacity(alice.address, capacity)
//
//    waitForBlockHeight(BigInteger.valueOf(3), 60, 2);
//    Thread.sleep(10000);
//
//    String txHash2 = getOriginCapacity(lockHash, 0).outPoint.cell.txHash;
//    String index2 = "0";
//    String blockHash2 = getOriginCapacity(lockHash, 0).outPoint.blockHash;
//    String capacity2 = "97000000000";
//    String codeHash2 = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";
//    String args2 = "0xada4e9f448cd306c56b101301a690e0a1b794c41";//args of alice  0x3a6a92e988ae0d1121a181046d948209b82289c5fc6c28c7da3c31cb512e70a9
//    CkbTransaction tx2 = sendTx(txHash2, index2, blockHash2, capacity2, codeHash2, args2,
//        privateKey);
//
////    Thread.sleep(10000);
////    String txHash3 = tx.result.transaction.hash;
////    String index3 = "0";
////    String blockHash3 = ckbService.getTransaction(txHash3).send().result.txStatus.blockHash;
////    String capacity3 = "5000000000";
////    String codeHash3 = "0xf1951123466e4479842387a66fabfd6b65fc87fd84ae8e6cd3053edb27fff2fd";//lock hash 0x51a7bdf0e861650a88764f12a7508c135e05fdceb831a8774e69e4b078ffe971
////    String args3 = "0x3506a3641c1695b78eec269e664012a745fa652d";//args of bobo address=ckt1q9gry5zgx5r2xequz62m0rhvy60xvsqj5azl5efd3knr83
////    String key = "0xcd6a7b65487308ffa26a42a8c3fe1877a68b13c69671a5130b0f8092196c8add";
////
////    Script aliceLock = new Script(codeHash, Collections.singletonList(args));
////
////    Long traceCapacity2 = 90000000000l;
////
////    CkbTransaction tx3 = sendTx(txHash3, index3, blockHash3, capacity3, codeHash3, args3,
////        key, String.valueOf(traceCapacity2), aliceLock);//alice.send_capacity(bobo.address, capacity)
////
////    Thread.sleep(10000);
////    String hash3 = tx3.result.transaction.hash;
////    int num = ckbService.getTransaction(hash3).send().result.transaction.outputs.size();
////    System.out.println("size: " + num);
//  }
//
//  public CkbTransaction sendTx(String txHash, String index, String blockHash, String capacity,
//      String codeHash, String args, String toPrivateKey)
//      throws Exception {
//    String version = "0";
//
//    Transaction tra = new Transaction(version, depences(), sendFrom(txHash, index, blockHash),
//        sendTo(capacity, codeHash, args),
//        Collections.singletonList(new Witness(Collections.emptyList())));
//
//    String hash = ckbService.computeTransactionHash(tra).send().getTransactionHash();
//    Transaction signedTx = tra.sign(Numeric.toBigInt(toPrivateKey), hash);
//
//    String hash2 = ckbService.sendTransaction(signedTx).send().getTransactionHash();
//    System.out.println("hash is: " + hash2);
//
//    CkbTransaction aTx = ckbService.getTransaction(hash2).send();
//
//    return aTx;
//  }
////  public CkbTransaction sendTx(String txHash, String index, String blockHash, String capacity,
////      String codeHash, String args, String toPrivateKey, String traceCapacity, Script mineLock)
////      throws Exception {
////    String version = "0";
////
////    Transaction tra = new Transaction(version, depences(), sendFrom(txHash, index, blockHash),
////        sendTo(capacity, codeHash, args, traceCapacity, mineLock),
////        Collections.singletonList(new Witness(Collections.emptyList())));
////
////    String hash = ckbService.computeTransactionHash(tra).send().getTransactionHash();
////    Transaction signedTx = tra.sign(Numeric.toBigInt(toPrivateKey), hash);
////
////    String hash2 = ckbService.sendTransaction(signedTx).send().getTransactionHash();
////    System.out.println("hash is: " + hash2);
////
////    CkbTransaction aTx = ckbService.getTransaction(hash2).send();
////
////    return aTx;
////  }
//
//  //output: to address
//  public List<CellOutput> sendTo(String capacity, String codeHash, String args) {
//    List<CellOutput> cellOutputs = new ArrayList<>();
//    Script lock = new Script(codeHash,
//        Collections.singletonList(args));
//    CellOutput cellOutput = new CellOutput(capacity, "0x", lock);
//    cellOutputs.add(cellOutput);
//
////    CellOutput traceCellOutput = new CellOutput(traceCapacity, "0x", mineLock);
////    cellOutputs.add(traceCellOutput);
//    return cellOutputs;
//  }
//
//  //input
//  public List<CellInput> sendFrom(String txHash, String index, String blockHash) {
//    List<CellInput> cellInputs = new ArrayList<>();
//    CellOutPoint cellOutPoint = new CellOutPoint(txHash, index);
//    OutPoint previousOutput = new OutPoint(blockHash, cellOutPoint);
//    CellInput cellInput = new CellInput(previousOutput, "0");
//    cellInputs.add(cellInput);
//    return cellInputs;
//  }
//
//  public List<OutPoint> depences() throws Exception {
//    List<OutPoint> deps = new ArrayList<>();
//    CellOutPoint cell0 = new CellOutPoint(
//        getBlockInfo(), "1");
//    OutPoint dd = new OutPoint(null, cell0);
//    deps.add(dd);
//
//    return deps;
//  }
//
//  public CellOutputWithOutPoint getOriginCapacity(String lockHash, int n) throws Exception {
//    long originCapacity;
//    Thread.sleep(10000);
//    List<CellOutputWithOutPoint> cells = ckbService
//        .getCellsByLockHash(lockHash, "545", "550")
//        .send()
//        .getCells();
//
//    originCapacity = Long.valueOf(cells.get(n).capacity);
//    printout("SDK liveCell origin capacity is: " + originCapacity);
//    originCapacity = originCapacity / 100000000;
//    printout("SDK deal originCapacity is: " + originCapacity);
//
//    return cells.get(n);
//  }
//
//  public String getBlockInfo() throws Exception {
//    waitForBlockHeight(BigInteger.valueOf(3), 60, 2);
//    String txhash = ckbService.getBlockByNumber("0").send().result.transactions.get(0).hash;
//    System.out.println("txhash: " + txhash);
//    return txhash;
//  }
//
//}
