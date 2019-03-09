package net.tntchina.hwid;

import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import net.tntchina.hwid.utils.WebUtils;

public class MainCheck {
	
	public static String tokens = "";
	public static String lastToken = "";
	
	static {
		MainCheck.tokens = WebUtils.sendGet("https://raw.githubusercontent.com/TNTChinaAAA/Token/master/Tokens.txt");
	}
	
	public static void main(String[] args) throws Exception {
		if (MainCheck.tokens == null) {
			MainCheck.tokens = WebUtils.sendGet("https://raw.githubusercontent.com/TNTChinaAAA/Token/master/Tokens.txt");
		}
		
		final String[] token = MainCheck.tokens.split(",");
		String str = JOptionPane.showInputDialog(null, "\u8bf7\u8f93\u5165\u60a8\u8d2d\u4e70\u7684\u0054\u006f\u006b\u0065\u006e", "\u8f93\u5165\u5bf9\u8bdd\u6846", JOptionPane.INFORMATION_MESSAGE);
		
		if (str == null) {
			JOptionPane.showMessageDialog(null, "\u4f60\u53d6\u6d88\u4ec0\u4e48\u610f\u601d\u003f", "\u4f60\u5988\u6b7b\u4e86", JOptionPane.ERROR_MESSAGE);
			
			try {
				MainCheck.main(args);
			} catch (StackOverflowError e) {
				e.printStackTrace();
			}
		}
		
		KeyData data = new KeyData(str);
		String strnew = data.encrypt(str);
		final List<String> token_list = Arrays.asList(token);
		
		if (!token_list.contains(strnew)) {
			JOptionPane.showMessageDialog(null, "\u0054\u006f\u006b\u0065\u006e\u4e0d\u6b63\u786e\u0021\u8bf7\u627e\u8d2d\u4e70\u4eba\u8981\u6b63\u786e\u7684\u0054\u006f\u006b\u0065\u006e\u0021", "\u4f60\u5988\u6b7b\u4e86", JOptionPane.ERROR_MESSAGE);
			
			try {
				MainCheck.main(args);
			} catch (StackOverflowError e) {
				e.printStackTrace();
			}
		} else {
			MainCheck.lastToken = strnew;
			JOptionPane.showMessageDialog(null, "\u5bc6\u7801\u6b63\u786e\u0021\u70b9\u51fb\u8fdb\u5165\u6e38\u620f\u0021", "\u606d\u559c\u4f60", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
}
