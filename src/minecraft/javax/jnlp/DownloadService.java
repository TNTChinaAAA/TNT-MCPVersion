package javax.jnlp;

import java.io.IOException;
import java.net.URL;
import javax.jnlp.DownloadServiceListener;

public interface DownloadService {
   boolean isResourceCached(URL var1, String var2);

   boolean isPartCached(String var1);

   boolean isPartCached(String[] var1);

   boolean isExtensionPartCached(URL var1, String var2, String var3);

   boolean isExtensionPartCached(URL var1, String var2, String[] var3);

   void loadResource(URL var1, String var2, DownloadServiceListener var3) throws IOException;

   void loadPart(String var1, DownloadServiceListener var2) throws IOException;

   void loadPart(String[] var1, DownloadServiceListener var2) throws IOException;

   void loadExtensionPart(URL var1, String var2, String var3, DownloadServiceListener var4) throws IOException;

   void loadExtensionPart(URL var1, String var2, String[] var3, DownloadServiceListener var4) throws IOException;

   void removeResource(URL var1, String var2) throws IOException;

   void removePart(String var1) throws IOException;

   void removePart(String[] var1) throws IOException;

   void removeExtensionPart(URL var1, String var2, String var3) throws IOException;

   void removeExtensionPart(URL var1, String var2, String[] var3) throws IOException;

   DownloadServiceListener getDefaultProgressWindow();
}
