package javax.jnlp;

import java.net.URL;

public interface BasicService {
   URL getCodeBase();

   boolean isOffline();

   boolean showDocument(URL var1);

   boolean isWebBrowserSupported();
}
