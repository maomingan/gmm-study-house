/*
 * Licensed to the Wiseco Software Corporation under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gmm.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HmacSHA1签名
 * 
 * @author Anxinkun
 */
public class HmacSHA1 {
    private static final String MAC_NAME = "HmacSHA1";

    /**
     * @param encryptText 待加密内容
     * @param encryptKey 加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = Base64.decodeBase64(encryptKey);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(StandardCharsets.UTF_8);
        // 完成 Mac 操作
        return Base64.encodeBase64String(mac.doFinal(text));
    }

    public static void main(String[] args) throws Exception {
        String d =
            HmacSHA1Encrypt("requestRefId=SJSREQ_201601010809108632A&secretId=KFZQpn74WFkmLPx3gnP",
                "Au2x3tCGRNqd62cd98okQVCN1Cozk2pC");
        System.out.println(d);
    }
}
