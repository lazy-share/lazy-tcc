package com.lazy.tcc.core.serializer;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>
 * SerializationFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public final class SerializationFactory {

    private SerializationFactory(){}

    private static volatile Serialization serialization;

    public static Serialization create() {
        if (serialization == null) {
            synchronized (SerializationFactory.class) {
                if (serialization == null) {
                    try {
                        serialization = SpiConfiguration.getInstance().getSeriClassImpl().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return serialization;
    }

    public static void main(String[] args) {


        Serialization serialization = SerializationFactory.create();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Transaction transaction = new Transaction().init();
        try {
            serialization.serialize(bos).writeObject(transaction);

            System.out.println(bos.toByteArray());

            Transaction transaction1 =  serialization.deserialize(new ByteArrayInputStream(bos.toByteArray()))
                    .readObject(Transaction.class);

            System.out.println(JSON.toJSONString(transaction1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
