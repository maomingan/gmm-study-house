package com.gmm.score_skills;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用2位字符串来表示一个评分，用于将该2位字符设置在transid中，然后请求到数据源侧，在数据源侧根据2位字母得到分数，然后根据分数做抹除，以节省成本
 *
 * @author Gmm
 * @date 2024/11/22
 */
public class ScoreTransformerWithTwoChar {

    /**
     * 字符 到 5位2进制 的映射
     */
    private static Map<Character, String> mapping = new HashMap<>();
    /**
     * 5位2进制 到 字符 的映射
     */
    private static Map<String, Character> reverseMapping = new HashMap<>();

    static {
        // 5位2进制可以表示0-31共32个字符，所以随便使用32个字符来做mapping映射
        String tags = "0123456789abcdefghijklmnpqrstuvw";
        for (int i = 0; i < tags.length(); i++) {
            // 从0开始，将0-31的数字获得5位的二进制字符串，用来做映射
            String binary = Integer.toBinaryString(i);
            String withLeadingZeros = String.format("%5s", binary).replace(' ', '0');
            mapping.put(tags.charAt(i), withLeadingZeros);
            reverseMapping.put(withLeadingZeros, tags.charAt(i));
        }
    }

    public static int getScore(String cutOff) {
        String lowStr = cutOff.substring(0, 2);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < lowStr.length(); i++) {
            String binary = mapping.get(lowStr.charAt(i));
            if (StringUtils.hasText(binary)) {
                stringBuffer.append(binary);
            }
        }
        return Integer.parseInt(stringBuffer.toString(), 2);
    }

    public static String getCharString(int score) {
        String scoreBinary = Integer.toBinaryString(score);
        String score10Binary = String.format("%10s", scoreBinary).replace(' ','0');
        String scoreFirst5 = score10Binary.substring(0,5);
        String scoreLast5 = score10Binary.substring(5);
        Character scoreFirstChar = reverseMapping.get(scoreFirst5);
        Character scoreLastChar = reverseMapping.get(scoreLast5);
        String result = String.valueOf(scoreFirstChar)+String.valueOf(scoreLastChar);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getCharString(772));
        System.out.println(getScore("p4"));
    }

}
