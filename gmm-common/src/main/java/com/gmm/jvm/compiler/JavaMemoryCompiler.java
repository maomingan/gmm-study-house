package com.gmm.jvm.compiler;

import lombok.extern.slf4j.Slf4j;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Gmm
 * @date 2024/10/29
 */
@Slf4j
public class JavaMemoryCompiler {

    JavaCompiler compiler;
    StandardJavaFileManager stdManager;
    private static final List<String> DEFAULT_OPTIONS = Arrays.asList("-g", "-nowarn");

    public JavaMemoryCompiler() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.stdManager = compiler.getStandardFileManager(null, null, null);
    }


    /**
     * Compile a Java source file in memory.
     *
     * @param fileName
     *            Java file name, e.g. "Test.java"
     * @param source
     *            The source code as String.
     * @return The compiled results as Map that contains class name as key,
     *         class binary as value.
     * @throws IOException
     *             If compile error.
     */
    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result.booleanValue()) {
                throw new RuntimeException("Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }


    /**
     * Load class from compiled classes.
     *
     * @param name
     *            Full class name.
     * @param classBytes
     *            Compiled results as a Map.
     * @return The Class instance.
     * @throws ClassNotFoundException
     *             If class not found.
     * @throws IOException
     *             If load error.
     */
    public Class<?> loadClass(String name, Map<String, byte[]> classBytes) throws ClassNotFoundException, IOException {
        try (MemoryClassLoader classLoader = new MemoryClassLoader(classBytes)) {
            return classLoader.loadClass(name);
        }
    }

//    public Class<?> loadClass(String name, String packageName,byte[] content) throws ClassNotFoundException, IOException {
//        String realName = MessageFormat.format("{0}.{1}", packageName,name);
//        Map<String, byte[]> classBytes = new HashMap<>();
//        classBytes.put(realName, content);
//        return loadClass(realName,classBytes);
//    }

//    public Map<String, Class<?>> loadClassMap(Map<String, byte[]> classBytes) throws IOException {
//        Map<String, Class<?>> classMap = Maps.newHashMap();
//        long time1 = System.currentTimeMillis();
//        try (MemoryClassLoader classLoader = new MemoryClassLoader(classBytes)) {
//            //并行装载class
//            List<CompletableFuture<Pair<String, Class<?>>>> completableFutures = classBytes.keySet().stream().map(key -> CompletableFuture.supplyAsync(() -> {
//                try {
//                    return new Pair<String, Class<?>>(key, classLoader.loadClass(key));
//                } catch (Exception e) {
//                    log.error("Class load failed",e);
//                    throw new RuntimeException("Class load failed"+e);
//                }
//            })).collect(Collectors.toList());
//            completableFutures.stream().map(CompletableFuture::join).forEach(pair -> classMap.put(pair.getFirst(), pair.getSecond()));
//        }
//        log.info("load{}个class，耗时:{}ms",classBytes.keySet().size(),System.currentTimeMillis()-time1);
//        return classMap;
//    }


    /**
     * 	获取一个内存的classLoader
     * @param packageName
     * @param content
     * @return
     * @throws IOException
     */
    public MemoryClassLoader getMemoryClassLoader(String packageName,Map<String,byte[]> content) throws IOException {
        Map<String,byte[]> realContents = new HashMap<>();
        content.forEach((key,byteContent)->{
            realContents.put(MessageFormat.format("{0}.{1}", packageName,key), byteContent);
        });
        try (MemoryClassLoader classLoader = new MemoryClassLoader(realContents)) {
            return classLoader;
        }
    }

    public Map<String, byte[]> compileClass(String className, String script) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(className+".java", script);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, new DiagnosticListener<JavaFileObject>() {
                @Override
                public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                    if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                        log.error("compile error:className:{}, javaCode:{}, error message:{}.", className, script, diagnostic);
                    }
                }
            }, DEFAULT_OPTIONS, null, Arrays.asList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result.booleanValue()) {
                throw new RuntimeException("Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }


    public Map<String, byte[]> compileClass(Map<String,String> javaCodeMap) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            List<JavaFileObject> fileObjectLst = new ArrayList<>();
            javaCodeMap.forEach((key, value) -> {
                String tmp = key.substring(0, key.lastIndexOf(JsonParserConstant.DOT));
                String javaClassName = key.substring(tmp.lastIndexOf(JsonParserConstant.DOT) + 1);
                fileObjectLst.add(manager.makeStringSource(javaClassName.trim(), value));
            });

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostic -> {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                    throw new RuntimeException("compile error" + diagnostic);
                }
            }, DEFAULT_OPTIONS, null, fileObjectLst);
            Boolean result = task.call();
            if (result == null || !result) {
                throw new RuntimeException("Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }

}
