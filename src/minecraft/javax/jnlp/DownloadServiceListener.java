package javax.jnlp;

import java.net.URL;

public interface DownloadServiceListener {
   void progress(URL var1, String var2, long var3, long var5, int var7);

   void validating(URL var1, String var2, long var3, long var5, int var7);

   void upgradingArchive(URL var1, String var2, int var3, int var4);

   void downloadFailed(URL var1, String var2);
}
