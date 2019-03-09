package javax.jnlp;

import java.net.URL;

public interface ExtensionInstallerService {
   String getInstallPath();

   String getExtensionVersion();

   URL getExtensionLocation();

   void hideProgressBar();

   void hideStatusWindow();

   void setHeading(String var1);

   void setStatus(String var1);

   void updateProgress(int var1);

   void installSucceeded(boolean var1);

   void installFailed();

   void setJREInfo(String var1, String var2);

   void setNativeLibraryInfo(String var1);

   String getInstalledJRE(URL var1, String var2);
}
