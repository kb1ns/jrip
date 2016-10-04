package org.pixyonly.jripper.bytecode;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public interface Info<I extends Info> {

    I read(Iterator<Byte> stream);

    void link();

    long getBytesCount();

    int getContentSize();
}
