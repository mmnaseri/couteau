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

package com.agileapes.couteau.strings.token.impl;

import com.agileapes.couteau.strings.token.Token;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/23/13, 12:03 PM)
 */
public class TaggedToken implements Token {

    private final Token token;
    private final int tag;

    public TaggedToken(Token token) {
        this(token, NO_TAG);
    }

    public TaggedToken(Token token, int tag) {
        this.token = token;
        this.tag = tag;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public int getStart() {
        return token.getStart();
    }

    @Override
    public int getEnd() {
        return token.getEnd();
    }

    @Override
    public int getLength() {
        return token.getLength();
    }

    @Override
    public boolean isTagged() {
        return tag != NO_TAG;
    }

}