package com.gmm.jvm;

import com.gmm.jvm.compiler.JavaMemoryCompiler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Gmm
 * @date 2024/10/29
 */
public class Test {

    private static final String codePath = "/Users/ganmaomin/IdeaProjects/workspace_study/gmm-study-house/gmm-common/src/main/java/com/gmm/jvm/code";

    public static void main(String[] args) throws Exception {

        JavaMemoryCompiler compiler = new JavaMemoryCompiler();
        // 读取整个文件内容为一个String
        String filePath = codePath + "/comp01";
        String fileJavaCode = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        final Map<String, byte[]> classBytes = compiler.compile("Comp.java", fileJavaCode);
        final Class<?> clazz = compiler.loadClass("com.gmm.comp.Comp", classBytes);
        final Object instance = clazz.newInstance();
        final Method doRunMethod = clazz.getMethod("doRun");
        // 执行doRun方法
        doRunMethod.invoke(instance);

        // 重新load新的class
        String newFilePath = codePath + "/comp02";
        String newFileJavaCode = new String(Files.readAllBytes(Paths.get(newFilePath)), StandardCharsets.UTF_8);
        final Map<String, byte[]> newClassBytes = compiler.compile("Comp.java", newFileJavaCode);
        final Class<?> newClazz = compiler.loadClass("com.gmm.comp.Comp", newClassBytes);
        final Object newInstance = newClazz.newInstance();
        final Method newDoRunMethod = newClazz.getMethod("doRun");
        // 执行doRun方法
        newDoRunMethod.invoke(newInstance);

    }

}
