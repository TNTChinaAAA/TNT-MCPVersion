package javax.jnlp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jnlp.FileContents;

public interface PersistenceService {
   int CACHED = 0;
   int TEMPORARY = 1;
   int DIRTY = 2;

   long create(URL var1, long var2) throws MalformedURLException, IOException;

   FileContents get(URL var1) throws MalformedURLException, IOException, FileNotFoundException;

   void delete(URL var1) throws MalformedURLException, IOException;

   String[] getNames(URL var1) throws MalformedURLException, IOException;

   int getTag(URL var1) throws MalformedURLException, IOException;

   void setTag(URL var1, int var2) throws MalformedURLException, IOException;
}
