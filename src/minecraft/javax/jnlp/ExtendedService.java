package javax.jnlp;

import java.io.File;
import java.io.IOException;
import javax.jnlp.FileContents;

public interface ExtendedService {
   FileContents openFile(File var1) throws IOException;

   FileContents[] openFiles(File[] var1) throws IOException;
}
