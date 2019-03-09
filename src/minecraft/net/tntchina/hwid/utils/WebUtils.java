package net.tntchina.hwid.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebUtils {
	
	public static String sendGet(final String url) {
		String result = "";
		BufferedReader in = null;

		try {
			final String urlNameString = url;
			final URL realUrl = new URL(urlNameString);
			final URLConnection connection = realUrl.openConnection();
			connection.setDoOutput(true);
			connection.setReadTimeout(99781);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) ;Chrome 33333");
			connection.connect();
			//final Map<String, List<String>> map = connection.getHeaderFields();

			//for (Map.Entry<String, List<String>> s : map.entrySet()) {
				//System.out.println("Key: " + s.getKey() + " | " + "Value: " + s.getValue() + ".");
			//}

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";

			while ((line = in.readLine()) != null) {
				result = String.valueOf(result) + line + "\n";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}

		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception ex3) {
			ex3.printStackTrace();
		}

		return result;
	}
}
