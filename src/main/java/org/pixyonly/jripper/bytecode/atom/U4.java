package org.pixyonly.jripper.bytecode.atom;

import org.pixyonly.jripper.bytecode.Atom;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U4 implements Atom {

    private byte b1, b2, b3, b4;

    private boolean isReference = false;

//    public U4(){}

    //    public U4(boolean isReference, byte b1, byte b2, byte b3, byte b4) {
//        this.isReference = isReference;
//        this.b1 = b1;
//        this.b2 = b2;
//        this.b3 = b3;
//        this.b4 = b4;
//    }
    public U4(Iterator<Byte> stream) {
        b1 = stream.next();
        b2 = stream.next();
        b3 = stream.next();
        b4 = stream.next();
    }

    public U4(boolean isReference, Iterator<Byte> stream) {
        this.isReference = isReference;
        b1 = stream.next();
        b2 = stream.next();
        b3 = stream.next();
        b4 = stream.next();
    }

//    @Override
//    public U4 read(Iterator<Byte> stream) throws ClassResolveException {
//        b1 = stream.next();
//        b2 = stream.next();
//        b3 = stream.next();
//        b4 = stream.next();
//        return this;
//    }

    @Override
    public long getBytesCount() {
        return 4;
    }

    @Override
    public boolean isReference() {
        return isReference;
    }

    @Override
    public int intValue() {
        return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
    }

    @Override
    public long longValue() {
        return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
    }

}
