package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Field implements Info<Field> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_VOLATILE = 0x0040;

    public static final int ACC_TRANSIENT = 0x0080;

    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ENUM = 0x4000;

    private U2 accessFlag;

    private U2 nameRef;

    private U2 descriptorRef;

    private Attributes attributes;

    private static final String[] FOCUS_ATTACHMENT = {
            "Deprecated", "Signature", "Synthetic", "RuntimeVisibleAnnotations", "RuntimeInvisibleAnnotations"
    };

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
        if ((ops & ACC_STATIC) == ACC_STATIC)
            sb.append("static ");
        if ((ops & ACC_FINAL) == ACC_FINAL) {
            sb.append("final ");
        } else if ((ops & ACC_VOLATILE) == ACC_VOLATILE) {
            sb.append("volatile ");
        }
        if ((ops & ACC_ENUM) == ACC_ENUM)
            sb.append("enum ");
        if ((ops & ACC_TRANSIENT) == ACC_TRANSIENT)
            sb.append("transient ");
        return sb.toString();
    }


    @Override
    public Field read(Iterator<Byte> stream) throws ClassResolveException {
        accessFlag = new U2(false, stream);
        nameRef = new U2(true, stream);
        descriptorRef = new U2(true, stream);
        attributes = new Attributes().read(stream);
        return this;
    }

    @Override
    public long getBytesCount() {
        return accessFlag.getBytesCount() + descriptorRef.getBytesCount() +
                nameRef.getBytesCount() + attributes.getBytesCount();
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
        return String.format("%s%s %s", resolveAccessFlag(), descriptorRef.referenceValue(), nameRef.referenceValue());
    }
}
