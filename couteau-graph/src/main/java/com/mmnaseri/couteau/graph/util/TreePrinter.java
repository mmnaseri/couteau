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

package com.mmnaseri.couteau.graph.util;

import com.mmnaseri.couteau.graph.tree.node.TreeNode;
import com.mmnaseri.couteau.graph.tree.walk.TreeNodeProcessor;
import com.mmnaseri.couteau.graph.tree.walk.TreeProcessor;
import com.mmnaseri.couteau.graph.tree.walk.impl.DefaultTreeProcessor;
import com.mmnaseri.couteau.graph.tree.walk.impl.PreOrderNodeProcessor;

import java.io.PrintStream;

/**
 * This class is a utility for printing out the nodes of a tree in a human-readable fashion
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (2013/7/25, 11:15)
 */
public class TreePrinter {

    protected final PrintStream output;
    private final NodePrinter nodePrinter;
    private final TreeProcessor treeProcessor;

    public TreePrinter(PrintStream output) {
        this(output, new NodePrinter() {
            @Override
            public void print(TreeNode node, PrintStream output) {
                output.print(node == null ? "null" : node.toString());
            }
        });
    }

    public TreePrinter(PrintStream output, NodePrinter nodePrinter) {
        this.output = output;
        this.nodePrinter = nodePrinter;
        this.treeProcessor = new DefaultTreeProcessor();
    }

    public void print(TreeNode root) {
        //noinspection unchecked
        treeProcessor.process(root, getProcessor());
    }

    private TreeNodeProcessor getProcessor() {
        return new PreOrderNodeProcessor() {

            @Override
            public void process(Object input) {
                if (input == null) {
                    output.println();
                    return;
                }
                if (!(input instanceof TreeNode)) {
                    return;
                }
                final TreeNode node = (TreeNode) input;
                final int indent = node.getDepth();
                for (int i = 0; i < indent; i ++) {
                    output.print("\t");
                }
                nodePrinter.print(node, output);
                output.println();
            }
        };
    }

}
