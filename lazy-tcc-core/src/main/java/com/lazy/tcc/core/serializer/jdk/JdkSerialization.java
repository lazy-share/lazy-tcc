

package com.lazy.tcc.core.serializer.jdk;


import com.lazy.tcc.core.serializer.ObjectInput;
import com.lazy.tcc.core.serializer.ObjectOutput;
import com.lazy.tcc.core.serializer.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JdkSerialization implements Serialization {

    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new JdkObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new JdkObjectInput(input);
    }
}
