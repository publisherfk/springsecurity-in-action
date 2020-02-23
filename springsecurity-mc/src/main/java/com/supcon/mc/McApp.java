package com.supcon.mc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.supcon.mc.service.PrinterService;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

public class McApp {
    public static String SOURCE_PATH = "F:\\com\\supcon\\mc\\service\\impl\\PrinterServiceImpl.java";

    public static void main(String args[]) throws IOException {
        String result = Files.asCharSource(new File(SOURCE_PATH), Charsets.UTF_8).read();
        SimpleJavaFileObject fileObject = new JavaSourceFromString("com.supcon.mc.service.impl.PrinterServiceImpl", result);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, Arrays.asList(fileObject));
        task.call();
        ClassLoader classLoader = fileManager.getClassLoader(null);
        try {
            Class<?> printClass = classLoader.loadClass("com.supcon.mc.service.impl.PrinterServiceImpl");
            PrinterService printerService = (PrinterService) printClass.newInstance();
            printerService.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义JavaSourceFromString，作为源代码的抽象文件
    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        public JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }

    }

    //JavaClassFileObject，代表class的文件抽象
    static class JavaClassFileObject extends SimpleJavaFileObject {
        ByteArrayOutputStream outputStream = null;

        public JavaClassFileObject(String className, Kind kind) {
            super(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind);
            outputStream = new ByteArrayOutputStream();
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return this.outputStream;
        }

        public byte[] getClassBytes() {
            return outputStream.toByteArray();
        }
    }

    static class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private JavaClassFileObject classFileObject;

        protected ClassFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            classFileObject = new JavaClassFileObject(className, kind);
            return classFileObject;
        }

        @Override
        public ClassLoader getClassLoader(Location location) {
            return new ClassLoader() {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    byte[] classBytes = classFileObject.getClassBytes();
                    return super.defineClass(name, classBytes, 0, classBytes.length);
                }
            };
        }
    }
}

