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

package com.mmnaseri.couteau.reflection.beans.impl;

import com.mmnaseri.couteau.reflection.test.Book;
import com.mmnaseri.couteau.reflection.test.PublishedBook;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/9/13, 3:13 PM)
 */
public class ConstructorBeanInitializerTest {

    @Test
    public void testBeanInitialization() throws Exception {
        final ConstructorBeanInitializer initializer = new ConstructorBeanInitializer();
        final Book book = initializer.initialize(Book.class, new Class[0]);
        Assert.assertNotNull(book);
    }

    @Test
    public void testBeanInstantiationWithConstructorArgs() throws Exception {
        final ConstructorBeanInitializer initializer = new ConstructorBeanInitializer();
        final PublishedBook publishedBook = initializer.initialize(PublishedBook.class, new Class[]{Book.class, int.class, String.class, String.class}, new Book(), 2000, "Some Publisher", "123456");
        Assert.assertNotNull(publishedBook);
    }

}
