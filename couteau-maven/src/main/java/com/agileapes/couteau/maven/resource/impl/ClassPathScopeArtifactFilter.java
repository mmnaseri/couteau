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

package com.agileapes.couteau.maven.resource.impl;

import com.agileapes.couteau.basics.api.Filter;
import com.agileapes.couteau.maven.resource.ClassPathScope;
import org.apache.maven.artifact.Artifact;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/3/13, 1:19 PM)
 */
public class ClassPathScopeArtifactFilter implements Filter<Artifact> {

    private Set<String> scopes = new HashSet<String>();

    public ClassPathScopeArtifactFilter(Collection<ClassPathScope> scopes) {
        for (ClassPathScope scope : scopes) {
            this.scopes.add(scope.toString());
        }
    }

    public ClassPathScopeArtifactFilter(ClassPathScope... scopes) {
        this(Arrays.asList(scopes));
    }

    @Override
    public boolean accepts(Artifact item) {
        return scopes.contains(item.getScope());
    }

}