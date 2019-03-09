package javax.jnlp;

import java.io.IOException;
import java.io.InputStream;
import javax.jnlp.FileContents;

public interface FileSaveService {
   FileContents saveFileDialog(String var1, String[] var2, InputStream var3, String var4) throws IOException;

   FileContents saveAsFileDialog(String var1, String[] var2, FileContents var3) throws IOException;
}
