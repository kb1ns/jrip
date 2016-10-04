package org.pixyonly.jripper.bytecode.atom;

import org.pixyonly.jripper.bytecode.Atom;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U8 implements Atom {

    private byte b1, b2, b3, b4, b5, b6, b7, b8;

    private boolean isReference = false;

//    public U8(){}

//    public U8(boolean isReference, byte b1, byte b2, byte b3, byte b4,
//              byte b5, byte b6, byte b7, byte b8) {
//        this.isReference = isReference;
//        this.b1 = b1;
//        this.b2 = b2;
//        this.b3 = b3;
//        this.b4 = b4;
//        this.b5 = b5;
//        this.b6 = b6;
//        this.b7 = b7;
//        this.b8 = b8;
//    }

    public U8(boolean isReference, Iterator<Byte> stream) {
        this.isReference = isReference;
        b1 = stream.next();
        b2 = stream.next();
        b3 = stream.next();
        b4 = stream.next();
        b5 = stream.next();
        b6 = stream.next();
        b7 = stream.next();
        b8 = stream.next();
    }


    @Override
    public long getBytesCount() {
        return 8;
    }

    @Override
    public boolean isReference() {
        return isReference;
    }

    @Override
    public int intValue() {
        throw new ArithmeticException();
    }

    @Override
    public long longValue() {
        return ((long) ((b1 << 24) + (b2 << 16) + (b3 << 8) + b4) << 32) + (b5 << 24) + (b6 << 16) + (b7 << 8) + b8;
    }
}
