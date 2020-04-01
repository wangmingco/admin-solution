package co.wangming.adminserver.util;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created By WangMing On 2020-03-03
 **/
public class MessageDigestUtil {

    private static final byte[] HMAC_KEY = "1585727195822".getBytes();

    public static String md5(String content) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(content.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，" + e.toString());
        }
    }

    public static String hmacSha512Digest(String content) {
        HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, HMAC_KEY);
        return hmac.hmacHex(content);
    }
}
