package com.gmm.jvm.compiler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.StreamReadFeature;

/**
 * @author Gmm
 * @date 2024/10/29
 */
public interface JsonParserConstant {

    JsonFactory FACTORY = new JsonFactoryBuilder().enable(StreamReadFeature.IGNORE_UNDEFINED).build();
    String DOT = ".", DOLLAR = "$", TREE_AT_SEPARATOR = "/", COMMA = ",", LINE = "-", DOUBLE_LINE = "--", SPACE = " ",
            NULL = "null";

}
