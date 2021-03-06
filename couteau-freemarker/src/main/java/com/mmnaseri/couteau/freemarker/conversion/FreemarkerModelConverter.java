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

package com.mmnaseri.couteau.freemarker.conversion;

import com.mmnaseri.couteau.freemarker.model.GenericFreemarkerModel;
import com.mmnaseri.couteau.freemarker.utils.FreemarkerUtils;
import com.mmnaseri.couteau.reflection.beans.BeanWrapper;
import com.mmnaseri.couteau.reflection.beans.BeanWrapperFactory;
import com.mmnaseri.couteau.reflection.beans.impl.MethodBeanWrapper;
import com.mmnaseri.couteau.reflection.convert.BeanConverter;
import com.mmnaseri.couteau.reflection.convert.GenericBeanConverter;
import com.mmnaseri.couteau.reflection.convert.impl.DefaultBeanConverter;
import com.mmnaseri.couteau.reflection.error.BeanConversionException;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.template.SimpleCollection;
import freemarker.template.TemplateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This generic bean converter will take an arbitrary object as input and return an instance of
 * {@link TemplateModel} reflecting the properties within that object. This will allow for easy
 * conversion of any Java bean into a valid input for a Freemarker template.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/13, 5:09 PM)
 */
public class FreemarkerModelConverter implements GenericBeanConverter<Object, TemplateModel> {

    private final BeanConverter converter;

    public FreemarkerModelConverter() {
        converter = new DefaultBeanConverter(new BeanWrapperFactory() {
            @Override
            public <E> BeanWrapper<E> getBeanWrapper(E bean) {
                if (bean instanceof GenericFreemarkerModel) {
                    //noinspection unchecked
                    return (BeanWrapper<E>) bean;
                }
                return new MethodBeanWrapper<E>(bean);
            }
        }, new FreemarkerConversionStrategy());
    }

    @Override
    public TemplateModel convert(Object bean) throws BeanConversionException {
        if (bean instanceof Collection<?>) {
            final Collection<?> collection = (Collection<?>) bean;
            final ArrayList<TemplateModel> models = new ArrayList<TemplateModel>();
            for (Object object : collection) {
                models.add(convert(object));
            }
            return new SimpleCollection(models);
        } else if (bean instanceof Map<?, ?>) {
            final HashMap<TemplateModel, TemplateModel> map = new HashMap<TemplateModel, TemplateModel>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) bean).entrySet()) {
                map.put(convert(entry.getKey()), convert(entry.getValue()));
            }
            return new MapModel(map, BeansWrapper.getDefaultInstance());
        } else if (FreemarkerUtils.canConvert(bean)) {
            return FreemarkerUtils.convertItem(bean);
        }
        return converter.convert(bean, GenericFreemarkerModel.class);
    }

}
