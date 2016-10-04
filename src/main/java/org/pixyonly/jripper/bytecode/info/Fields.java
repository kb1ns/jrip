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
public class Fields implements Info<Fields> {

    private U2 count;

    private List<Field> fields;

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public Fields read(Iterator<Byte> stream) throws ClassResolveException {
        count = new U2(true, stream);
        fields = new ArrayList<>(count.intValue());
        for (int i = 0; i < count.intValue(); i++)
            fields.add(new Field().read(stream));
        return this;
    }

    @Override
    public long getBytesCount() {
        return count.getBytesCount() + fields.stream().mapToLong(Field::getBytesCount).sum();
    }

    @Override
    public void link() {
        fields.forEach(Field::link);
    }

    @Override
    public int getContentSize() {
        return fields.size();
    }
}
