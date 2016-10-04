package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Method implements Info<Method> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SYNCHRONIZED = 0x0020;

    public static final int ACC_BRIDGE = 0x0040;

    public static final int ACC_VARARGS = 0x0080;

    public static final int ACC_NATIVE = 0x0100;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_STRICTFP = 0x0800;

    public static final int ACC_SYNTHETIC = 0x1000;

    private U2 accessFlag;

    private U2 nameRef;

    private U2 descriptorRef;

    private Attributes attributes;

    public String resolveAccessFlag() {
        int ops = accessFlag.intValue();
        StringBuilder sb = new StringBuilder();
        if ((ops & ACC_PUBLIC) == ACC_PUBLIC) {
            sb.append("public ");
        } else if ((ops & ACC_PRIVATE) == ACC_PRIVATE) {
            sb.append("private ");
        } else if ((ops & ACC_PROTECTED) == ACC_PROTECTED) {
            sb.append("protected ");
        }
        //no more verify
        if ((ops & ACC_STATIC) == ACC_STATIC)
            sb.append("static ");
        if ((ops & ACC_FINAL) == ACC_FINAL)
            sb.append("final ");
        if ((ops & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED)
            sb.append("synchronized ");
        if ((ops & ACC_ABSTRACT) == ACC_ABSTRACT)
            sb.append("abstract ");
        if ((ops & ACC_STRICTFP) == ACC_STRICTFP)
            sb.append("strictfp ");
        if ((ops & ACC_NATIVE) == ACC_NATIVE)
            sb.append("native ");
        return sb.toString();
    }

    @Override
    public Method read(Iterator<Byte> stream) throws ClassResolveException {
        accessFlag = new U2(false, stream);
        nameRef = new U2(true, stream);
        descriptorRef = new U2(true, stream);
        attributes = new Attributes().read(stream);
        return this;
    }

    @Override
    public long getBytesCount() {
        return accessFlag.getBytesCount() + nameRef.getBytesCount() +
                descriptorRef.getBytesCount() + attributes.getBytesCount();
    }

    @Override
    public void link() {
        ConstantItem descriptorItem = ConstantPool.instance.get(descriptorRef);
        if (descriptorItem.value() == null) {
            descriptorItem.link();
        }
        ConstantItem nameItem = ConstantPool.instance.get(nameRef);
        if (nameItem.value() == null) {
            nameItem.link();
        }
    }

    @Override
    public int getContentSize() {
        return 1;
    }

    @Override
    public String toString() {
        return String.format("%s%s %s {\n%s\n}", resolveAccessFlag(),
                nameRef.referenceValue(),descriptorRef.referenceValue(),attributes);
    }
}
