package javax.jnlp;

import java.io.IOException;
import javax.jnlp.FileContents;

public interface FileOpenService {
   FileContents openFileDialog(String var1, String[] var2) throws IOException;

   FileContents[] openMultiFileDialog(String var1, String[] var2) throws IOException;
}
