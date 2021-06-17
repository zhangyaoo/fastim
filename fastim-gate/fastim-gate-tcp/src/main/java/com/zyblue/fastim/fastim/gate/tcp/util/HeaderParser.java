package com.zyblue.fastim.fastim.gate.tcp.util;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.AppendableCharSequence;

/**
 * @author will
 * @date 2021/4/29 18:02
 */
public class HeaderParser implements ByteProcessor {
    private final AppendableCharSequence seq;
    private final int maxLength;
    private int size;

    public HeaderParser(AppendableCharSequence seq, int maxLength) {
        this.seq = seq;
        this.maxLength = maxLength;
    }

    public AppendableCharSequence parse(ByteBuf buffer) {
        final int oldSize = size;
        seq.reset();
        int i = buffer.forEachByte(this);
        if (i == -1) {
            size = oldSize;
            return null;
        }
        buffer.readerIndex(i + 1);
        return seq;
    }

    public boolean isHttp(ByteBuf buffer) {
        buffer.markReaderIndex();
        buffer.markWriterIndex();
        boolean skied = false;
        final int wIdx = buffer.writerIndex();
        int rIdx = buffer.readerIndex();
        while (wIdx > rIdx) {
            int c = buffer.getUnsignedByte(rIdx++);
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                rIdx--;
                skied = true;
                break;
            }
        }
        if (skied) {
            AppendableCharSequence line = this.parse(buffer);
            if (line == null) {
                skied = false;
            }
        }
        buffer.readerIndex(rIdx);

        buffer.resetReaderIndex();
        buffer.resetWriterIndex();
        return skied;
    }

    @Override
    public boolean process(byte value) throws Exception {
        char nextByte = (char) (value & 0xFF);
        if (nextByte == HttpConstants.LF) {
            int len = seq.length();
            // Drop CR if we had a CRLF pair
            if (len >= 1 && seq.charAtUnsafe(len - 1) == HttpConstants.CR) {
                -- size;
                seq.setLength(len - 1);
            }
            return false;
        }

        increaseCount();

        seq.append(nextByte);
        return true;
    }

    protected final void increaseCount() {
        if (++ size > maxLength) {
            // TODO: Respond with Bad Request and discard the traffic
            //    or close the connection.
            //       No need to notify the upstream handlers - just log.
            //       If decoding a response, just throw an exception.
            throw newException(maxLength);
        }
    }

    protected TooLongFrameException newException(int maxLength) {
        return new TooLongFrameException("HTTP header is larger than " + maxLength + " bytes.");
    }
}
