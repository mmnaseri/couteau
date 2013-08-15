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

package com.agileapes.couteau.concurrency.task;

import com.agileapes.couteau.concurrency.error.TaskFailureException;

/**
 * This interface encapsulates a single task that is to be performed
 * concurrently.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/8/15, 5:53)
 */
public interface Task {

    /**
     * This method should embody the operations whose completion will be regarded
     * the same as that of the whole task
     * @throws TaskFailureException in case any errors should prevent the task from
     * being successfully completed.
     */
    void perform() throws TaskFailureException;

}
