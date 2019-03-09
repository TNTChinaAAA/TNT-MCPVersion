package javax.jnlp;

import javax.jnlp.UnavailableServiceException;

public interface ServiceManagerStub {
   Object lookup(String var1) throws UnavailableServiceException;

   String[] getServiceNames();
}
