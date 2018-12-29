package com.lazy.tcc.core.serializer;

import java.io.IOException;

/**
 * Object output.
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public interface ObjectOutput extends DataOutput {

    /**
     * write object.
     *
     * @param obj object.
     */
    void writeObject(Object obj) throws IOException;

}