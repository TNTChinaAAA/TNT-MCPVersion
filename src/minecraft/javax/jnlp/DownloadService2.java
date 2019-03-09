package javax.jnlp;

import java.io.IOException;

public interface DownloadService2 {
   int ALL = 0;
   int APPLICATION = 1;
   int APPLET = 2;
   int EXTENSION = 3;
   int JAR = 4;
   int IMAGE = 5;
   int CLASS = 6;

   DownloadService2.ResourceSpec[] getCachedResources(DownloadService2.ResourceSpec var1);

   DownloadService2.ResourceSpec[] getUpdateAvailableResources(DownloadService2.ResourceSpec var1) throws IOException;

   public static class ResourceSpec {
      private String url;
      private String version;
      private int type;
      private long size;
      private long lastModified;
      private long expirationDate;

      public ResourceSpec(String var1, String var2, int var3) {
         this.url = var1;
         this.version = var2;
         this.type = var3;
         this.size = -1L;
      }

      public String getUrl() {
         return this.url;
      }

      public String getVersion() {
         return this.version;
      }

      public int getType() {
         return this.type;
      }

      public long getSize() {
         return this.size;
      }

      public long getLastModified() {
         return this.lastModified;
      }

      public long getExpirationDate() {
         return this.expirationDate;
      }

      static {
         /*DownloadService2Impl.setResourceSpecAccess(new DownloadService2Impl.ResourceSpecAccess() {
            public void setSize(DownloadService2.ResourceSpec var1, long var2) {
               var1.size = var2;
            }

            public void setLastModified(DownloadService2.ResourceSpec var1, long var2) {
               var1.lastModified = var2;
            }

            public void setExpirationDate(DownloadService2.ResourceSpec var1, long var2) {
               var1.expirationDate = var2;
            }
         });*/
      }
   }
}
