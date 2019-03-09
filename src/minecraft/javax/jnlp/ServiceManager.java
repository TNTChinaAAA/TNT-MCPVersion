package javax.jnlp;

import javax.jnlp.ServiceManagerStub;
import javax.jnlp.UnavailableServiceException;

public final class ServiceManager {
   private static ServiceManagerStub _stub = null;

   public static Object lookup(String var0) throws UnavailableServiceException {
      if(_stub != null) {
         return _stub.lookup(var0);
      } else {
         throw new UnavailableServiceException("uninitialized");
      }
   }

   public static String[] getServiceNames() {
      return _stub != null?_stub.getServiceNames():null;
   }

   public static synchronized void setServiceManagerStub(ServiceManagerStub var0) {
      if(_stub == null) {
         _stub = var0;
      }

   }
}
