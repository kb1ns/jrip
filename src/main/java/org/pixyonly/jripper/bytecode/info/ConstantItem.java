package org.pixyonly.jripper.bytecode.info;


import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U1;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.atom.U4;
import org.pixyonly.jripper.bytecode.atom.U8;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public abstract class ConstantItem implements Info<ConstantItem> {

    protected U1 tag;

    protected int constantIndex;

    public int getConstantIndex() {
        return constantIndex;
    }

    protected ConstantItem(U1 tag, int constantIndex) {
        this.tag = tag;
        this.constantIndex = constantIndex;
    }

    @Override
    public int getContentSize() {
        return 1;
    }

    public abstract String value();

    public static ConstantItem fetchItem(U1 tag, int constantIndex, Iterator<Byte> stream) throws ClassResolveException {
        switch (tag.value()) {
            case ConstantPool.CONSTANT_UTF8_TAG:
                return new ConstantUtf8(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_INTEGER_TAG:
                return new ConstantInteger(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_CLASS_TAG:
                return new ConstantClass(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_DOUBLE_TAG:
                return new ConstantDouble(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_FIELDREF_TAG:
                return new ConstantFieldRef(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_FLOAT_TAG:
                return new ConstantFloat(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_INTERFACEMETHODREF_TAG:
                return new ConstantInterfaceMethodRef(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_LONG_TAG:
                return new ConstantLong(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_METHODREF_TAG:
                return new ConstantMethodRef(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_METHODTYPE_TAG:
                return new ConstantMethodRef(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_NAMEANDTYPE_TAG:
                return new ConstantNameAndType(tag, constantIndex).read(stream);
            case ConstantPool.CONSTANT_STRING_TAG:
                return new ConstantString(tag, constantIndex).read(stream);
        }
        throw new ClassResolveException();
    }

    @Override
    public String toString() {
        return String.format("#%d(%s)->%s", constantIndex,
                this.getClass().getSimpleName(),
                value());
    }

    public static class ConstantUtf8 extends ConstantItem {

        private U2 length;

        private List<U1> bytes;

        private String utf8;

        public ConstantUtf8(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            length = new U2(false, stream);
            int size = length.intValue();
            bytes = new ArrayList<>(size);
            byte[] array = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes.add(new U1(stream));
                array[i] = bytes.get(i).value();
            }
            //TODO : utf-8压缩编码
            utf8 = new String(array, Charset.forName("utf-8"));
            return this;
        }

        @Override
        public long getBytesCount() {
            return length.getBytesCount() + bytes.size();
        }

        @Override
        public void link() {

        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantClass extends ConstantItem {

        private U2 pointer;

        private String utf8;

        public ConstantClass(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            pointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return pointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem item = ConstantPool.instance.get(pointer);
            // TODO the item may be null
            if (item.value() == null) {
                item.link();
            }
            utf8 = item.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantInteger extends ConstantItem {

        private U4 bytes;

        public ConstantInteger(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            bytes = new U4(false, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return bytes.getBytesCount();
        }

        @Override
        public void link() {

        }

        @Override
        public String value() {
            return String.valueOf(bytes.intValue());
        }
    }

    public static class ConstantFloat extends ConstantItem {

        private U4 bytes;

        protected ConstantFloat(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            bytes = new U4(false, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return bytes.getBytesCount();
        }

        @Override
        public void link() {

        }

        @Override
        public String value() {
            //TODO: float编码
            return String.valueOf(bytes.intValue());
        }

    }

    public static class ConstantLong extends ConstantItem {

        private U8 bytes;

        protected ConstantLong(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            bytes = new U8(false, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return bytes.getBytesCount();
        }

        @Override
        public void link() {

        }

        @Override
        public String value() {
            return String.valueOf(bytes.longValue());
        }
    }

    public static class ConstantDouble extends ConstantItem {

        private U8 bytes;

        protected ConstantDouble(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            bytes = new U8(false, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return bytes.getBytesCount();
        }

        @Override
        public void link() {

        }

        @Override
        public String value() {
            //TODO: double编码
            return String.valueOf(bytes.longValue());
        }

    }

    public static class ConstantString extends ConstantItem {

        private U2 pointer;

        private String utf8;

        protected ConstantString(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            pointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return pointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem item = ConstantPool.instance.get(pointer);
            if (item.value() == null) {
                item.link();
            }
            utf8 = item.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantFieldRef extends ConstantItem {

        private U2 classPointer;

        private U2 namePointer;

        private String utf8;

        protected ConstantFieldRef(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            classPointer = new U2(true, stream);
            namePointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return classPointer.getBytesCount() + namePointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem classItem = ConstantPool.instance.get(classPointer);
            if (classItem.value() == null) {
                classItem.link();
            }
            ConstantItem nameItem = ConstantPool.instance.get(namePointer);
            if (nameItem.value() == null) {
                nameItem.link();
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantMethodRef extends ConstantItem {

        private U2 classPointer;

        private U2 namePointer;

        private String utf8;

        protected ConstantMethodRef(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            classPointer = new U2(true, stream);
            namePointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return classPointer.getBytesCount() + namePointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem classItem = ConstantPool.instance.get(classPointer);
            if (classItem.value() == null) {
                classItem.link();
            }
            ConstantItem nameItem = ConstantPool.instance.get(namePointer);
            if (nameItem.value() == null) {
                nameItem.link();
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantInterfaceMethodRef extends ConstantItem {

        private U2 classPointer;

        private U2 namePointer;

        private String utf8;

        protected ConstantInterfaceMethodRef(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            classPointer = new U2(true, stream);
            namePointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return classPointer.getBytesCount() + namePointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem classItem = ConstantPool.instance.get(classPointer);
            if (classItem.value() == null) {
                classItem.link();
            }
            ConstantItem nameItem = ConstantPool.instance.get(namePointer);
            if (nameItem.value() == null) {
                nameItem.link();
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantNameAndType extends ConstantItem {

        private U2 namePointer;

        private U2 descriptorPointer;

        private String utf8;

        protected ConstantNameAndType(U1 tag, int constantIndex) {
            super(tag, constantIndex);
        }

        @Override
        public ConstantItem read(Iterator<Byte> stream) throws ClassResolveException {
            namePointer = new U2(true, stream);
            descriptorPointer = new U2(true, stream);
            return this;
        }

        @Override
        public long getBytesCount() {
            return namePointer.getBytesCount() + descriptorPointer.getBytesCount();
        }

        @Override
        public void link() {
            ConstantItem descriptorItem = ConstantPool.instance.get(descriptorPointer);
            if (descriptorItem.value() == null) {
                descriptorItem.link();
            }
            ConstantItem nameItem = ConstantPool.instance.get(namePointer);
            if (nameItem.value() == null) {
                nameItem.link();
            }
            String name = nameItem.value();
            String descriptor = descriptorItem.value();
            utf8 = String.format("%s(%s)", name, descriptor);
        }

        @Override
        public String value() {
            return utf8;
        }
    }
}
