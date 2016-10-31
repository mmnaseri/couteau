/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AgileApes, Ltd.
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

package com.mmnaseri.couteau.context.impl;

import com.mmnaseri.couteau.context.contract.OrderedBean;

import java.util.Comparator;

/**
 * This is a comparator that is designed to assist in ordering beans. Beans that do not implement
 * {@link OrderedBean} are assigned a {@link OrderedBean#NEUTRAL_PRECEDENCE}. Beans that do are
 * taken to have the precedence they declare.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/27/13, 4:21 PM)
 */
public class OrderedBeanComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        Integer first = o1 instanceof OrderedBean ? ((OrderedBean) o1).getOrder() : OrderedBean.NEUTRAL_PRECEDENCE;
        Integer second = o2 instanceof OrderedBean ? ((OrderedBean) o2).getOrder() : OrderedBean.NEUTRAL_PRECEDENCE;
        return first.compareTo(second);
    }

}
