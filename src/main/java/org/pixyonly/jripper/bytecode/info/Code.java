package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.Atom;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.Operator;
import org.pixyonly.jripper.bytecode.atom.U1;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pixyonly on 4/7/16.
 */
public class Code implements Info<Code> {

    private Operator operator;

    private List<U1> parameters = new LinkedList<>();

    @Override
    public Code read(Iterator<Byte> stream) throws ClassResolveException {
        operator = Operator.valueOf(Byte.toUnsignedInt(stream.next()));
        for (int i = 0; i < operator.parameterCount; i++) {
            parameters.add(new U1(stream));
        }
        return this;
    }

    @Override
    public long getBytesCount() {
        return 1 + parameters.stream().mapToLong(Atom::getBytesCount).sum();
    }

    @Override
    public void link() {

    }

    @Override
    public int getContentSize() {
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operator.name()).append(" ");
        parameters.forEach(p -> sb.append(p.hexValue()).append(" "));
        return sb.toString();
    }
}
