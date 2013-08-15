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

package com.agileapes.couteau.concurrency.error;

/**
 * This exception denotes a situation in which a task is being assigned to a
 * {@link com.agileapes.couteau.concurrency.worker.TaskWorker} who is already
 * assigned a task.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/8/15, 7:41)
 */
public class TaskCompletionFailedException extends TaskFailureException {

    public TaskCompletionFailedException(String message) {
        super(message);
    }

}
