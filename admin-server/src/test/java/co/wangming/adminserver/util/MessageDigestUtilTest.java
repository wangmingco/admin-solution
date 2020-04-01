package co.wangming.adminserver.util;

import org.junit.Test;

/**
 * Created By WangMing On 2020-04-01
 **/
public class MessageDigestUtilTest {

    @Test
    public void test() {
        String c = MessageDigestUtil.hmacSha512Digest("bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a");
        System.out.println(c);

    }
}
