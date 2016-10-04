package org.pixyonly.jripper.bytecode.atom;

import org.pixyonly.jripper.bytecode.Atom;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U1 implements Atom {

    private byte b;

//    public U1() {}

//    public U1(byte b) {
//        this.b = b;
//    }

    public U1(final Iterator<Byte> stream) {
        b = stream.next();
    }

//    @Override
//    public U1 read(Iterator<Byte> stream) throws ClassResolveException {
//        b = stream.next();
//        return this;
//    }

    public byte value() {
        return b;
    }

    public String hexValue() {
        return String.format("%02x", b);
    }

    @Override
    public long getBytesCount() {
        return 1;
    }

    @Override
    public boolean isReference() {
        return false;
    }

    @Override
    public int intValue() {
        return Byte.toUnsignedInt(b);
    }

    @Override
    public long longValue() {
        return Byte.toUnsignedLong(b);
    }

}
