package org.pixyonly.jripper.bytecode.info;


import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.atom.U4;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Attribute implements Info<Attribute> {

    private U2 nameRef;

    private U4 count;

    private List<Byte> attributes;

    private Attribute content;

    @Override
    public Attribute read(Iterator<Byte> stream) throws ClassResolveException {
        nameRef = new U2(true, stream);
        count = new U4(false, stream);
        attributes = new ArrayList<>(count.intValue());
        for (int i = 0; i < count.intValue(); i++)
            attributes.add(stream.next());
        String type = ConstantPool.instance.get(nameRef).value();
        if ("Code".equals(type))
            content = new CodeAttribute(attributes);
        return this;
    }

    @Override
    public long getBytesCount() {
        return nameRef.getBytesCount() + count.getBytesCount() + attributes.size();
    }

    @Override
    public void link() {
        String type = ConstantPool.instance.get(nameRef).value();
        if ("Code".equals(type))
            content = new CodeAttribute(attributes);
    }

    @Override
    public int getContentSize() {
        return 0;
    }

    @Override
    public String toString() {
        return content != null ? content.toString() : "";
    }

    public static class CodeAttribute extends Attribute {

        private U2 maxStack;

        private U2 maxLocals;

        private U4 codeLength;

        private List<Code> codes;

        private U2 exceptionLength;

        private List<ExceptionInfo> exceptionInfos;

        private U2 attributeCount;

        private List<Attribute> attributes;

        public CodeAttribute(List<Byte> content) {
            Iterator<Byte> ite = content.iterator();
            maxStack = new U2(false, ite);
            maxLocals = new U2(false, ite);
            codeLength = new U4(false, ite);
            codes = new LinkedList<>();
            int skip = (int) (maxStack.getBytesCount() + maxLocals.getBytesCount() + codeLength.getBytesCount());
            List<Byte> codeBody = content.subList(skip, skip + codeLength.intValue());
            Iterator<Byte> codeBodyIte = codeBody.iterator();
            while(codeBodyIte.hasNext()){
                codes.add(new Code().read(codeBodyIte));
            }
            //TODO ignore exception info
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Code:\n");
            codes.forEach(c -> sb.append(c).append("\n"));
            return sb.toString();
        }
    }

    public static class ExceptionInfo implements Info<ExceptionInfo> {

        @Override
        public ExceptionInfo read(Iterator<Byte> stream) throws ClassResolveException {
            return null;
        }

        @Override
        public long getBytesCount() {
            return 0;
        }

        @Override
        public void link() {

        }

        @Override
        public int getContentSize() {
            return 0;
        }
    }
}
