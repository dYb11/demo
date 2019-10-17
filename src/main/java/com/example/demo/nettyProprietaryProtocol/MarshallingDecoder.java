package com.example.demo.nettyProprietaryProtocol;


import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.Unmarshaller;

/**
 * 使用jboss序列化工具解码
 */
import java.io.IOException;

public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    /**
     * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
     * If the size of the received object is greater than {@code 1048576} bytes,
     * a {@link StreamCorruptedException} will be raised.
     *
     * @throws IOException
     *
     */
    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    protected Object decode(ByteBuf in) throws Exception {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
    }
}
