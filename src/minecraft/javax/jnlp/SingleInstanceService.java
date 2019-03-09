package javax.jnlp;

import javax.jnlp.SingleInstanceListener;

public interface SingleInstanceService {
   void addSingleInstanceListener(SingleInstanceListener var1);

   void removeSingleInstanceListener(SingleInstanceListener var1);
}
