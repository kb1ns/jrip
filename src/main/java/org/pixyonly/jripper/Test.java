package org.pixyonly.jripper;

import org.pixyonly.jripper.bytecode.ClassResource;
import org.pixyonly.jripper.bytecode.info.Clazz;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pixyonly on 4/20/16.
 */
public class Test {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ClassResource r = new ClassResource(new URI("file:///Users/pixyonly/Engineering/tmp/Hello.class"));
        Clazz clazz = new Clazz().read(r.getStream());


        System.out.println(clazz.resolveAccessFlag() + clazz.getClazzNameRef().referenceValue() + " {");
//        clazz.getFields().link();
        clazz.getFields().getFields().forEach(System.out::println);
//        clazz.getMethods().link();
        clazz.getMethods().getMethods().forEach(System.out::println);
        System.out.println("}");
    }
}
