package org.pixyonly.jripper.bytecode.info;


import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Methods implements Info<Methods> {

    private U2 count;

    private List<Method> methods;

    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public Methods read(Iterator<Byte> stream) throws ClassResolveException {
        count = new U2(false, stream);
        methods = new ArrayList<>(count.intValue());
        for (int i = 0; i < count.intValue(); i++) {
            methods.add(new Method().read(stream));
        }
        return this;
    }

    @Override
    public long getBytesCount() {
        return count.getBytesCount() + methods.stream().mapToLong(Method::getBytesCount).sum();
    }

    @Override
    public void link() {
        methods.forEach(Method::link);
    }

    @Override
    public int getContentSize() {
        return methods.size();
    }
}
