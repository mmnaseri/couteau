package com.agileapes.couteau.freemarker.model;

import com.agileapes.couteau.basics.api.Filter;
import com.agileapes.couteau.freemarker.api.Invokable;

import java.util.Collection;

import static com.agileapes.couteau.basics.collections.CollectionWrapper.with;

/**
 * This method model is designed to help with both checking if single items match a
 * given criteria and to filter out an entire collection based on that criteria.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/8/31, 17:08)
 */
public abstract class FilteringMethodModel<E> extends TypedMethodModel {

    @Invokable
    public Collection<E> filter(Collection<E> collection) {
        //noinspection unchecked
        return with(collection).keep(new Filter<E>() {
            @Override
            public boolean accepts(E item) {
                return filter(item);
            }
        }).list();
    }

    protected abstract boolean filter(E item);

}
