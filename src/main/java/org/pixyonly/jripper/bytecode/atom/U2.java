package org.pixyonly.jripper.bytecode.atom;


import org.pixyonly.jripper.bytecode.Atom;
import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.info.ConstantItem;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U2 implements Atom {

    private byte b1, b2;

    private boolean isReference = false;

//    public U2(){}

    public U2(boolean isReference, Iterator<Byte> stream) {
        this.isReference = isReference;
        b1 = stream.next();
        b2 = stream.next();
    }

//    public U2(boolean isReference, byte b1, byte b2) {
//        this.isReference = isReference;
//        this.b1 = b1;
//        this.b2 = b2;
//    }

//    @Override
//    public U2 read(Iterator<Byte> stream) throws ClassResolveException {
//        b1 = stream.next();
//        b2 = stream.next();
//        return this;
//    }

    @Override
    public long getBytesCount() {
        return 2;
    }

    @Override
    public boolean isReference() {
        return isReference;
    }

    @Override
    public int intValue() {
        return (b1 << 8) + b2;
    }

    @Override
    public long longValue() {
        return (b1 << 8) + b2;
    }

    public ConstantItem referenceValue() {
        if (isReference)
            return ConstantPool.instance.get(this);
        return null;
    }
}
