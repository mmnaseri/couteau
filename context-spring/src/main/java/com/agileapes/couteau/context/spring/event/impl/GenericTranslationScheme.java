package com.agileapes.couteau.context.spring.event.impl;

import com.agileapes.couteau.context.contract.Event;
import com.agileapes.couteau.context.spring.event.TranslationScheme;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (6/29/13, 4:32 PM)
 */
public class GenericTranslationScheme implements TranslationScheme {

    @Override
    public boolean handles(Event event) {
        return true;
    }

    @Override
    public ApplicationEvent translate(Event originalEvent) {
        final Method[] methods = ReflectionUtils.getAllDeclaredMethods(originalEvent.getClass());
        final GenericApplicationEvent applicationEvent = new GenericApplicationEvent(originalEvent.getSource());
        for (Method method : methods) {
            if (!isGetter(method)) {
                continue;
            }
            try {
                final Object value = method.invoke(originalEvent);
                final String propertyName = StringUtils.uncapitalize(method.getName().substring(method.getName().startsWith("get") ? 3 : 2));
                applicationEvent.setProperty(propertyName, value);
            } catch (Exception ignored) {
            }
        }
        return applicationEvent;
    }

    static boolean isGetter(Method method) {
        return !(!Modifier.isPublic(method.getModifiers()) || method.getParameterTypes().length != 0 ||
                method.getReturnType().equals(void.class)) && !(!method.getName().matches("get[A-Z].*") ||
                (!method.getReturnType().equals(boolean.class) && !method.getName().matches("is[A-Z].*")));
    }

    @Override
    public void fillIn(Event originalEvent, ApplicationEvent translated) {
        if (!(translated instanceof GenericApplicationEvent)) {
            return;
        }
        GenericApplicationEvent applicationEvent = (GenericApplicationEvent) translated;
        final Enumeration<?> propertyNames = applicationEvent.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String property = (String) propertyNames.nextElement();
            final Object value = applicationEvent.getProperty(property);
            final Method method = ReflectionUtils.findMethod(originalEvent.getClass(), "set" + StringUtils.capitalize(property));
            if (method == null || !Modifier.isPublic(method.getModifiers()) || method.getParameterTypes().length != 1 || !method.getReturnType().equals(void.class) || !method.getParameterTypes()[0].isInstance(value)) {
                continue;
            }
            try {
                method.invoke(originalEvent, value);
            } catch (Exception ignored) {
            }
        }
    }

}
