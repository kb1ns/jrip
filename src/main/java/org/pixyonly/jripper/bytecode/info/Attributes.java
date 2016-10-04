package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Attribute link before read
 * Created by pixyonly on 16/1/28.
 */
public class Attributes implements Info<Attributes> {

    private U2 count;

    private List<Attribute> attributes;

    @Override
    public Attributes read(Iterator<Byte> stream) throws ClassResolveException {
        count = new U2(false, stream);
        attributes = new ArrayList<>(count.intValue());
        for (int i = 0; i < count.intValue(); i++) {
            attributes.add(new Attribute().read(stream));
        }
        return this;
    }

    @Override
    public long getBytesCount() {
        return count.getBytesCount() + attributes.stream().mapToLong(Attribute::getBytesCount).sum();
    }

    @Override
    public void link() {
        attributes.forEach(Attribute::link);
    }

    @Override
    public int getContentSize() {
        return 0;
    }

    @Override
    public String toString() {
        return attributes.toString();
    }
}
