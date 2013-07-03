package com.agileapes.couteau.context.contract;

import com.agileapes.couteau.context.error.RegistryException;

/**
 * Bean processors are interceptors that will operate on any given bean of a certain type,
 * and return a processed -- possibly different -- version of the bean in question.
 *
 * The bean processor <em>should not</em> return {@code null}, even if the case might be handled
 * by some registries.
 *
 * @see Context#addBeanProcessor(BeanProcessor)
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (6/27/13, 4:11 PM)
 */
public interface BeanProcessor<E> {

    /**
     * This processor is called exactly before the bean is put in the registry. This gives you a chance
     * to process a bean only once and update it and ensuring that the bean is pre-processed.
     * @param bean    the bean
     * @param name    the name of the bean, as registered with the context
     * @return the (processed) bean
     * @throws RegistryException
     */
    E postProcessBeforeRegistration(E bean, String name) throws RegistryException;

    /**
     * This processor will run every time the bean is being accessed through a context. This will
     * give you the chance to update information in the bean that need to be updated everytime the
     * bean is being accessed.
     * @param bean    the bean
     * @param name    the name of the bean, as registered with the context
     * @return the (processed) bean
     * @throws RegistryException
     */
    E postProcessBeforeAccess(E bean, String name) throws RegistryException;

}