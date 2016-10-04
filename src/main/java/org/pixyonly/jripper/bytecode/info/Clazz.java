package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.atom.U4;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.Iterator;

/**
 * Created by pixyonly on 4/8/16.
 */
public class Clazz implements Info<Clazz> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SUPER = 0x0020;

    public static final int ACC_INTERFACE = 0x0200;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ANNOTATION = 0x2000;

    public static final int ACC_ENUM = 0x4000;

    private U4 magicNumber;

    private U2 minorVersion;

    private U2 majorVersion;

    private U2 accessFlag;

    private U2 clazzNameRef;

    private U2 superClazzNameRef;

    private Interfaces interfaces;

    private Fields fields;

    private Methods methods;

    private Attributes attributes;

    public U4 getMagicNumber() {
        return magicNumber;
    }

    public U2 getMinorVersion() {
        return minorVersion;
    }

    public U2 getMajorVersion() {
        return majorVersion;
    }

    public U2 getAccessFlag() {
        return accessFlag;
    }

    public U2 getClazzNameRef() {
        return clazzNameRef;
    }

    public U2 getSuperClazzNameRef() {
        return superClazzNameRef;
    }

    public Interfaces getInterfaces() {
        return interfaces;
    }

    public Fields getFields() {
        return fields;
    }

    public Methods getMethods() {
        return methods;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String resolveAccessFlag() {
        StringBuilder sb = new StringBuilder();
        if ((ACC_PUBLIC & accessFlag.intValue()) == ACC_PUBLIC)
            sb.append("public ");
        if ((ACC_FINAL & accessFlag.intValue()) == ACC_FINAL)
            sb.append("final ");
        if (isInterface()) {
            sb.append("interface ");
        } else if (isAbstract()) {
            sb.append("abstract class ");
        } else if ((ACC_ENUM & accessFlag.intValue()) == ACC_ENUM) {
            sb.append("enum ");
        } else if ((ACC_ANNOTATION & accessFlag.intValue()) == ACC_ANNOTATION) {
            sb.append("@interface ");
        } else {
            sb.append("class ");
        }
        return sb.toString();
    }

    public boolean isAbstract() {
        return (ACC_ABSTRACT & accessFlag.intValue()) == ACC_ABSTRACT;
    }

    public boolean isInterface() {
        return (ACC_INTERFACE & accessFlag.intValue()) == ACC_INTERFACE;
    }

    private void init(Iterator<Byte> stream) {
        magicNumber = new U4(false, stream);
        minorVersion = new U2(false, stream);
        majorVersion = new U2(false, stream);
        ConstantPool.instance.init(stream);
    }

    @Override
    public Clazz read(Iterator<Byte> stream) throws ClassResolveException {
        init(stream);
        accessFlag = new U2(false, stream);
        clazzNameRef = new U2(true, stream);
        superClazzNameRef = new U2(true,stream);
        interfaces = new Interfaces().read(stream);
        fields = new Fields().read(stream);
        methods = new Methods().read(stream);
        attributes = new Attributes().read(stream);
        return this;
    }

    @Override
    public void link() {

    }

    @Override
    public long getBytesCount() {
        return 0;
    }

    @Override
    public int getContentSize() {
        return 0;
    }


}
