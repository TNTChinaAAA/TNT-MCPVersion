package javax.jnlp;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

public interface PrintService {
   PageFormat getDefaultPage();

   PageFormat showPageFormatDialog(PageFormat var1);

   boolean print(Pageable var1);

   boolean print(Printable var1);
}
