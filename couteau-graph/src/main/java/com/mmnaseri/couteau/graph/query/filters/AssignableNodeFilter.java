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

package com.mmnaseri.couteau.graph.query.filters;

import com.mmnaseri.couteau.graph.node.ConfigurableNodeFilter;
import com.mmnaseri.couteau.graph.node.Node;

/**
 * Filters nodes based on their super-type
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (2013/7/30, 15:38)
 */
public class AssignableNodeFilter<N extends Node> implements ConfigurableNodeFilter<N> {

    private Class<?> from = Object.class;

    @Override
    public void setAttribute(String name, String value) {
        if ("from".equals(name)) {
            try {
                if (!value.matches("[^\\.]*\\..*?")) {
                    value = "java.lang." + value;
                }
                from = Class.forName(value);
            } catch (ClassNotFoundException e) {
                from = Object.class;
            }
        }
    }

    @Override
    public boolean accepts(N item) {
        return from.isInstance(item);
    }
}
