/*
 * Copyright (c) 2013. AgileApes (http://www.agileapes.scom/), and
 * associated organization.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.couteau.graph.tree.walk;

import com.agileapes.couteau.graph.tree.node.impl.BinaryTreeNode;

/**
 * This interface allows for processing of a node in a binary tree, right between its children's processing
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/7/25, 14:01)
 */
public interface BinaryTreeNodeProcessor<N extends BinaryTreeNode> extends TreeNodeProcessor<N> {

    /**
     * This method will be called after the node's left child is processed and before the right
     * child's processing begins
     * @param node    the node to be processed
     */
    void processBetweenChildren(N node);

}
