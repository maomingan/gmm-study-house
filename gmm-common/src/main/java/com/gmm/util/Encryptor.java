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

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

    public static String generatPassword() {
        String[] keyChars =
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "Q",
                "W", "E", "R", "T", "Y", "U", "I", "O", "P", "a", "s", "d", "f", "g", "h", "j", "k", "l", "A", "S",
                "D", "F", "G", "H", "J", "K", "L", "z", "x", "c", "v", "b", "n", "m", "Z", "X", "C", "V", "B", "N",
                "M", "!", "@", "#", "$", "%", "-", "=", "_", "+"};
        StringBuffer sb = new StringBuffer(8);
        for (int i = 0; i < 8; i++) {

            double random = Math.random() * (keyChars.length - 1);
            int index = (int)Math.round(random);

            sb.append(keyChars[index]);
        }

        return sb.toString();
    }

    public static String generateKey() {
        String[] keyChars =
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "Q",
                "W", "E", "R", "T", "Y", "U", "I", "O", "P", "a", "s", "d", "f", "g", "h", "j", "k", "l", "A", "S",
                "D", "F", "G", "H", "J", "K", "L", "z", "x", "c", "v", "b", "n", "m", "Z", "X", "C", "V", "B", "N",
                "M", "!", "@", "#", "$", "%", "-", "=", "_", "+"};
        StringBuffer sb = new StringBuffer(16);
        for (int i = 0; i < 16; i++) {

            double random = Math.random() * (keyChars.length - 1);
            int index = (int)Math.round(random);

            sb.append(keyChars[index]);

        }

        return sb.toString();
    }

    public static String encrypt2Base64Str(String strToEncrypt, String secretKey, String ivString) throws Exception {

        if (ivString.length() != 16) {
            throw new Exception("IV 字符串不等于16位");
        }

        byte[] iv = ivString.getBytes("UTF-8");
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        byte[] byteToEncrypt;

        String encryptedString = null;

        byteToEncrypt = strToEncrypt.getBytes("UTF-8");

        // Encrypt and Encode to Base64 String
        byte[] keyBytes = secretKey.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);

        encryptedString = Base64.encodeBase64String(cipher.doFinal(byteToEncrypt));

        return encryptedString;

    }

    public static String decryptBase64Str(String base64StrToDecrypt, String secretKey, String ivString)
        throws Exception {

        if (ivString.length() != 16) {
            throw new Exception("IV 字符串不等于16位");
        }

        byte[] iv = ivString.getBytes("UTF-8");
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Decode from Base64 String
        byte[] byteToDecrypt = Base64.decodeBase64(base64StrToDecrypt);

        // decrypt
        byte[] keyBytes = secretKey.getBytes();
        byte[] decryptedBytes = null;

        String decryptedString = null;
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding", "SunJCE");

        // encryption pass
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
        decryptedBytes = cipher.doFinal(byteToDecrypt);
        decryptedString = new String(decryptedBytes, "UTF-8");

        return decryptedString;
    }

}
