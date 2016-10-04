package org.pixyonly.jripper.bytecode;

import org.pixyonly.jripper.bytecode.exception.UnsupportedSchemeException;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pixyonly on 4/8/16.
 */
public class ClassResource {

    private byte[] bytes;

    public ClassResource(URI uri) throws IOException {
        if ("file://".equals(uri.getScheme())) {
            throw new UnsupportedSchemeException();
        }
        DataInputStream is = new DataInputStream(getInputStreamFromFile(uri));
        bytes = new byte[is.available()];
        is.readFully(bytes);
        is.close();
    }

    private InputStream getInputStreamFromFile(URI uri) throws FileNotFoundException {
        File f = new File(uri.getPath());
        return new FileInputStream(f);
    }

    public Iterator<Byte> getStream() {
        final List<Byte> list = new ArrayList<>(bytes.length);
        for (byte b : bytes)
            list.add(b);
        return list.iterator();
    }
}
