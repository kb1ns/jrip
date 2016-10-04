package org.pixyonly.jripper.bytecode;

import org.pixyonly.jripper.bytecode.exception.ClassResolveException;

import java.util.Iterator;

/**
 * Created by pixyonly on 16/1/28.
 */
public interface Segment<T extends Segment> {

    T read(Iterator<Byte> stream) throws ClassResolveException;

    long getBytesCount();
}
