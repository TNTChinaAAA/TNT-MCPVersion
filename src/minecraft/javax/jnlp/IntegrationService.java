package javax.jnlp;

public interface IntegrationService {
   boolean requestShortcut(boolean var1, boolean var2, String var3);

   boolean hasDesktopShortcut();

   boolean hasMenuShortcut();

   boolean removeShortcuts();

   boolean requestAssociation(String var1, String[] var2);

   boolean hasAssociation(String var1, String[] var2);

   boolean removeAssociation(String var1, String[] var2);
}
