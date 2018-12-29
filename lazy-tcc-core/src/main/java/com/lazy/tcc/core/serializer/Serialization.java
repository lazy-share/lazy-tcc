package com.lazy.tcc.core.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Serialization
 * This serializer component is referred from dubbo
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public interface Serialization {

    /**
     * create serializer
     *
     * @param output
     * @return serializer
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * create deserializer
     *
     * @param input
     * @return deserializer
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;
}
