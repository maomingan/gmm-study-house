package com.gmm.plugin_use;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Gmm
 * @date 2024/11/13
 */
@NoArgsConstructor
@Data
public class Test {

    @JsonProperty("a")
    private String a;
    @JsonProperty("b")
    private String b;
    @JsonProperty("c")
    private Integer c;

    public static void main(String[] args) {

        Test test = new Test();
        test.setA("");
        test.setB("");
        test.setC(0);

    }

    public TestDTO convert(Test test){
        TestDTO testDTO = new TestDTO();
        testDTO.setA(test.getA());
        testDTO.setB(test.getB());
        testDTO.setC(String.valueOf(test.getC()));
        return testDTO;
    }

    @Data
    class TestDTO{
        private String a;
        private String b;
        private String c;
    }

}
