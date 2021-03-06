/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Milad Naseri.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mmnaseri.couteau.lang.compiler.impl;

import com.mmnaseri.couteau.lang.compiler.SimpleSourceCompiler;
import com.mmnaseri.couteau.lang.error.CompileException;
import com.mmnaseri.couteau.lang.support.IdentifiedLanguageInput;
import com.mmnaseri.couteau.lang.support.LanguageInput;
import com.mmnaseri.couteau.lang.support.LanguageOutput;
import com.mmnaseri.couteau.lang.support.impl.Java;
import com.mmnaseri.couteau.reflection.cp.MappedClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/13, 8:08 PM)
 */
public class SimpleJavaSourceCompiler implements SimpleSourceCompiler<Java> {

    @SuppressWarnings("UnusedDeclaration")
    public static enum Option {
        CLASSPATH("-classpath", false),
        NO_WARNINGS("-nowarn", true),
        VERBOSE("-verbose", true),
        DEPRECATION_WARNING("-deprecation", true);

        private String directive;
        private boolean isBoolean;

        private Option(String directive, boolean aBoolean) {
            this.directive = directive;
            isBoolean = aBoolean;
        }

    }

    private static final String NEW_LINE_FEED = "\n";
    private final MappedClassLoader classLoader;
    private final Map<Option, String> options = new HashMap<Option, String>();

    public SimpleJavaSourceCompiler(ClassLoader classLoader) {
        this.classLoader = new MappedClassLoader(classLoader);
    }

    public void setOption(Option option, String value) {
        if (!option.isBoolean) {
            options.put(option, value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void enableOption(Option option) {
        if (option.isBoolean) {
            options.put(option, null);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void disableOption(Option option) {
        if (option.isBoolean) {
            options.remove(option);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void compile(LanguageInput<Java> source, LanguageOutput<Java> target) throws CompileException {
        String identifier;
        if (source instanceof IdentifiedLanguageInput<?>) {
            IdentifiedLanguageInput<?> input = (IdentifiedLanguageInput<?>) source;
            identifier = input.getIdentifier();
        } else {
            identifier = UUID.randomUUID().toString();
        }
        final BufferedReader reader = new BufferedReader(source.getReader());
        final StringBuilder content = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                content.append(line).append(NEW_LINE_FEED);
            }
        } catch (IOException e) {
            throw new CompileException("Failed to read input from the input provider", e);
        }
        final JavaCompiler systemCompiler = ToolProvider.getSystemJavaCompiler();
        final SingleLocationClassFileManager<StandardJavaFileManager> fileManager = new SingleLocationClassFileManager<StandardJavaFileManager>(classLoader, systemCompiler.getStandardFileManager(null, null, null));
        final List<String> options = getOptions();
        final Boolean compiled;
        try {
            compiled = systemCompiler.getTask(null, fileManager, null, options, null, Arrays.asList(new CharSequenceJavaFileObject(identifier, content))).call();
        } catch (Throwable e) {
            throw new CompileException("Compilation failed", e);
        }
        if (!compiled) {
            throw new CompileException("Compilation failed: " + identifier);
        }
        for (Map.Entry<String, JavaClassObject> entry : fileManager.getObjectMap().entrySet()) {
            classLoader.register(entry.getKey(), entry.getValue().getBytes());
        }
        final JavaClassObject classObject;
        try {
            classObject = fileManager.getClassObject(identifier);
        } catch (ClassNotFoundException e) {
            throw new CompileException("Actual class name is different from the given identifier: " + identifier, e);
        }
        final byte[] bytes = classObject.getBytes();
        try {
            target.getOutputStream().write(bytes);
        } catch (IOException e) {
            throw new CompileException("Failed to write output to the output provider", e);
        }
    }


    private List<String> getOptions() {
        final List<String> options = new ArrayList<String>();
        for (Map.Entry<Option, String> entry : this.options.entrySet()) {
            options.add(entry.getKey().directive);
            if (!entry.getKey().isBoolean) {
                options.add(entry.getValue());
            }
        }
        return options;
    }

    public MappedClassLoader getClassLoader() {
        return classLoader;
    }
}
