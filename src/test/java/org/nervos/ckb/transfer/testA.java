package org.nervos.ckb.transfer;

import java.util.Collections;
import org.nervos.ckb.util.items.Script;
import org.testng.annotations.Test;

/**
 * @Author wucj
 * @Date 2019/6/25 下午6:37
 * @Version 1.0
 */
public class testA {

  @Test
  public void A(){
     hash();
  }

  public String hash() {

    String configCodeHash = "0x94334bdda40b69bae067d84937aa6bbccf8acd0df6626d4b9ac70d4612a11933";
    String configArgs = "0x3954acece65096bfa81258983ddb83915fc56bd8";

    Script script = new Script(configCodeHash, Collections.singletonList(configArgs));

    String lockHash = script.getLockHash();

    System.out.println("lockhash: "+lockHash);
    return lockHash;
  }
}
