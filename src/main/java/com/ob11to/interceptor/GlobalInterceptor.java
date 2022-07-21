package com.ob11to.interceptor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serial;
import java.io.Serializable;

public class GlobalInterceptor extends EmptyInterceptor {

    @Serial
    private static final long serialVersionUID = 2847830996052365024L;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
