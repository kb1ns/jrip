package org.pixyonly.jripper.bytecode;


/**
 * Created by pixyonly on 16/1/28.
 */
public interface Atom {

    long getBytesCount();

    boolean isReference();

    int intValue();

    long longValue();

}
