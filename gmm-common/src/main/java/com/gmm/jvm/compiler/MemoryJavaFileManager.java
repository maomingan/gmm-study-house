package com.gmm.jvm.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;

/**
 * @author Gmm
 * @date 2024/10/29
 */
public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    // compiled classes in bytes:
    final Map<String, byte[]> classBytes = new HashMap<String, byte[]>();
    final Map<String, List<MemoryInputJavaClsObject>> clsMap     = new HashMap<>();

    public MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    public Map<String, byte[]> getClassBytes() {
        return new HashMap<>(this.classBytes);
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
        classBytes.clear();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, Kind kind,
                                               FileObject sibling) throws IOException {
        if (kind == Kind.CLASS) {
            return new MemoryOutputJavaFileObject(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    public JavaFileObject makeStringSource(String name, String code) {
        return new MemoryInputJavaFileObject(name, code);
    }

    static class MemoryInputJavaFileObject extends SimpleJavaFileObject {

        final String code;

        MemoryInputJavaFileObject(String name, String code) {
            super(URI.create("string:///" + name), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
            return CharBuffer.wrap(code);
        }
    }

    class MemoryOutputJavaFileObject extends SimpleJavaFileObject {
        final String name;

        MemoryOutputJavaFileObject(String name) {
            super(URI.create("string:///" + name), Kind.CLASS);
            this.name = name;
        }

        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
                @Override
                public void close() throws IOException {
                    out.close();
                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                }
            };
        }

    }
    static class MemoryInputJavaClsObject extends SimpleJavaFileObject {

        final byte[] code;

        MemoryInputJavaClsObject(String name, byte[] code) {
            super(URI.create("string:///" + name), Kind.CLASS);
            this.code = code;
        }
        @Override
        public InputStream openInputStream() throws IOException {
            return new ByteArrayInputStream(code);
        }
    }

    @Override
    public Iterable<JavaFileObject> list(Location action, String pkg, Set<JavaFileObject.Kind> kind, boolean recurse) throws IOException {

        Iterable<JavaFileObject> superFiles = super.list(action, pkg, kind, recurse);

        if(action == StandardLocation.CLASS_PATH && kind.contains(Kind.CLASS)) {
            List<JavaFileObject> result = new ArrayList<>();
            superFiles.forEach(result::add);
            if (clsMap.containsKey(pkg)){
                result.addAll(clsMap.get(pkg));
            }

            if(result.size()>0) {
                return result;
            }

        }
        return superFiles;
    }

    public void loadClsMap(Map<String, byte[]> dependenciesCls) {
        if (dependenciesCls!=null && dependenciesCls.size()>0){
            for (Map.Entry<String, byte[]> entry : dependenciesCls.entrySet()) {
                String key = entry.getKey();
                String pkgName = key.substring(0, key.lastIndexOf(JsonParserConstant.DOT));
                MemoryInputJavaClsObject clsFileObj = new MemoryInputJavaClsObject(key,
                        entry.getValue());
                List<MemoryInputJavaClsObject> pkgFileObjLst = clsMap.getOrDefault(pkgName,
                        new ArrayList<>());
                pkgFileObjLst.add(clsFileObj);
                clsMap.put(pkgName, pkgFileObjLst);
            }
        }
    }

    @Override
    public String inferBinaryName(Location location, JavaFileObject javaFileObject) {

        if((location == StandardLocation.CLASS_PATH )&& javaFileObject instanceof MemoryInputJavaClsObject) {
            String inferBinaryName =  javaFileObject.getName();
            return inferBinaryName;
        }
        String name = super.inferBinaryName(location, javaFileObject);

        return name;
    }

}
