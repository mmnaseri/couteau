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

package com.agileapes.couteau.strings.token;

/**
 * This interface defines the abstract meaning of a token as a {@code <start, end>} pair that can optionally
 * have a tag that will attach semantics to the token.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/23/13, 11:12 AM)
 */
public interface Token {

    public static final int NO_TAG = 0;

    /**
     * @return the (optional) tag associated with this token
     */
    int getTag();

    /**
     * @return the start position of the token relevant to the text it was generated from
     */
    int getStart();

    /**
     * @return the position of the last character of this token
     */
    int getEnd();

    /**
     * @return the length of the text this token denotes
     */
    int getLength();

    /**
     * @return {@code true} if the token has a tag assigned
     */
    boolean isTagged();

}