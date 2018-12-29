
package com.lazy.tcc.core.serializer.kryo;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.lazy.tcc.core.serializer.Cleanable;
import com.lazy.tcc.core.serializer.ObjectOutput;
import com.lazy.tcc.core.serializer.kryo.utils.KryoUtils;

import java.io.IOException;
import java.io.OutputStream;

public class KryoObjectOutput implements ObjectOutput, Cleanable {

    private Output output;
    private Kryo kryo;

    public KryoObjectOutput(OutputStream outputStream) {
        output = new Output(outputStream);
        this.kryo = KryoUtils.get();
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        output.writeBoolean(v);
        this.flushBuffer();
    }

    @Override
    public void writeByte(byte v) throws IOException {
        output.writeByte(v);
        this.flushBuffer();
    }

    @Override
    public void writeShort(short v) throws IOException {
        output.writeShort(v);
        this.flushBuffer();
    }

    @Override
    public void writeInt(int v) throws IOException {
        output.writeInt(v);
        this.flushBuffer();
    }

    @Override
    public void writeLong(long v) throws IOException {
        output.writeLong(v);
        this.flushBuffer();
    }

    @Override
    public void writeFloat(float v) throws IOException {
        output.writeFloat(v);
        this.flushBuffer();
    }

    @Override
    public void writeDouble(double v) throws IOException {
        output.writeDouble(v);
        this.flushBuffer();
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
        this.flushBuffer();
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            output.writeInt(len);
            output.write(v, off, len);
        }
        this.flushBuffer();
    }


    @Override
    public void writeUTF(String v) throws IOException {
        output.writeString(v);
        this.flushBuffer();
    }

    @Override
    public void writeObject(Object v) throws IOException {
        // TODO carries class info every time.
        kryo.writeClassAndObject(output, v);
        this.flushBuffer();
    }

    @Override
    public void flushBuffer() throws IOException {
        output.flush();
    }

    @Override
    public void cleanup() {
        KryoUtils.release(kryo);
        kryo = null;
    }
}
