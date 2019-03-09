package javax.jnlp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.jnlp.JNLPRandomAccessFile;

public interface FileContents {
   String getName() throws IOException;

   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream(boolean var1) throws IOException;

   long getLength() throws IOException;

   boolean canRead() throws IOException;

   boolean canWrite() throws IOException;

   JNLPRandomAccessFile getRandomAccessFile(String var1) throws IOException;

   long getMaxLength() throws IOException;

   long setMaxLength(long var1) throws IOException;
}
