package org.pixyonly.jripper.bytecode;


import org.pixyonly.jripper.bytecode.atom.U1;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;
import org.pixyonly.jripper.bytecode.info.ConstantItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public enum ConstantPool { // extends Info<ConstantPool> {

    instance;

    public final static byte CONSTANT_UTF8_TAG = 1;

    public final static byte CONSTANT_INTEGER_TAG = 3;

    public final static byte CONSTANT_FLOAT_TAG = 4;

    public final static byte CONSTANT_LONG_TAG = 5;

    public final static byte CONSTANT_DOUBLE_TAG = 6;

    public final static byte CONSTANT_CLASS_TAG = 7;

    public final static byte CONSTANT_STRING_TAG = 8;

    public final static byte CONSTANT_FIELDREF_TAG = 9;

    public final static byte CONSTANT_METHODREF_TAG = 10;

    public final static byte CONSTANT_INTERFACEMETHODREF_TAG = 11;

    public final static byte CONSTANT_NAMEANDTYPE_TAG = 12;

    public final static byte CONSTANT_METHODHANDLE_TAG = 15;

    public final static byte CONSTANT_METHODTYPE_TAG = 16;

    public final static byte CONSTANT_INVOKEDYNAMIC_TAG = 18;

    // begin from 1 and 0 means reference nothing in constant pool.
    private int poolSize;

    private List<ConstantItem> items;

    /**
     * Index of ConstantPool begin from 1.
     */
    public ConstantItem get(U2 constantIndex) {
        if (items == null)
            throw new IllegalStateException("ConstantPool not initialized.");
        return items.get(constantIndex.intValue() - 1);
    }

    public void link() {
        if (items == null)
            throw new IllegalStateException("ConstantPool not initialized.");
        items.forEach(org.pixyonly.jripper.bytecode.info.ConstantItem::link);
    }

    public void init(Iterator<Byte> stream) throws ClassResolveException {
        poolSize = new U2(false, stream).intValue() - 1;
        items = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            items.add(ConstantItem.fetchItem(new U1(stream), i + 1, stream));
        }
    }

    public long getBytesCount() {
        if (items == null)
            throw new IllegalStateException("ConstantPool not initialized.");
        return 2 + items.stream().mapToLong(ConstantItem::getBytesCount).sum();
    }

    public int getPoolSize() {
        if (items == null)
            throw new IllegalStateException("ConstantPool not initialized.");
        return poolSize;
    }
}
