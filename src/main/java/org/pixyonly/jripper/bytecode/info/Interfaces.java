package org.pixyonly.jripper.bytecode.info;

import org.pixyonly.jripper.bytecode.ConstantPool;
import org.pixyonly.jripper.bytecode.Info;
import org.pixyonly.jripper.bytecode.atom.U2;
import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Interfaces implements Info<Interfaces> {

    private U2 count;

    private List<U2> interfaces;

//    private List<String> interfaceName;

    public List<U2> getInterfaces() {
        return interfaces;
    }

//    public List<String> getInterfaceName() {
//        return interfaceName;
//    }

    @Override
    public void link() {
        for (U2 u2 : interfaces) {
            ConstantItem interfaceItem = ConstantPool.instance.get(u2);
            if (interfaceItem.value() == null)
                interfaceItem.link();
//            interfaceName.add(interfaceItem.value());
        }
    }

    @Override
    public Interfaces read(Iterator<Byte> stream) throws ClassResolveException {
        count = new U2(false, stream);
//        int size = interfaceCount.intValue();
        interfaces = new ArrayList<>(count.intValue());
        for (int i = 0; i < count.intValue(); i++) {
            interfaces.add(new U2(true, stream));
        }
        return this;
    }

    @Override
    public long getBytesCount() {
        return count.getBytesCount() + (count.intValue() << 2);
    }

    @Override
    public int getContentSize() {
        return count.intValue();
    }
}
