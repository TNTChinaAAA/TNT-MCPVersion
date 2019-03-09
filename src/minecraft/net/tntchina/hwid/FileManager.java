package net.tntchina.hwid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import net.tntchina.hwid.utils.WebUtils;

public class FileManager {
	
	public static void c(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		
		try {
			HttpClientContext context = new HttpClientContext();
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("TNTChina", "yhbyhb3274578216"));
			context.setCredentialsProvider(credentialsProvider);
			CloseableHttpResponse execute = client.execute(get, context);
			HttpEntity entity = execute.getEntity();
			InputStream in = entity.getContent();
			StringBuilder builder = new StringBuilder();
			BufferedReader bufreader = new BufferedReader(new InputStreamReader(in));
			String temp = "";
			
			while ((temp = bufreader.readLine()) != null) {
				builder.append(temp);
			}

			System.out.println(builder.toString());
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final void main(String... args) throws Exception {
		final FileManager fileManager = new FileManager();
		final String am = WebUtils.sendGet("https://gitee.com/TNTChina/Token/raw/master/Tokens.txt" +  "?username=" + "liaic" + "&password=" + "nt123456");
		//FileManager.c("https://gitee.com/TNTChina/Token/raw/master/Tokens.txt");
		System.out.println(am);
		fileManager.addToken("H:\\Token专用\\我的Token.txt", 200000);
		fileManager.addRebofTokenToFile("H:\\Token专用\\我的RebofToken.txt", "H:\\Token专用\\我的Token.txt");
		fileManager.getFilesToRebofToken("H:\\Token专用\\github.txt");
	}
	
	private final List<String> rebofs = new ArrayList<String>();
	
	public FileManager() {
		
	}
	
	public void addToken(String outpath, int number) throws IOException {
		File file = new File(outpath);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		final FileWriter writer = new FileWriter(file, true);
		
		for (int i = 0; i < number; i++) {
			writer.write("Key: " + RandomStringUtils.randomAlphanumeric(25) + System.lineSeparator());
		}
		
		writer.close();
	}
	
	public void addRebofTokenToFile(String outpath, String readPath) throws Exception {
		File file = new File(outpath);
		File file1 = new File(readPath);
		FileWriter writer = new FileWriter(file);
		FileReader reader = new FileReader(file1);
		LineNumberReader numberReader = new LineNumberReader(reader);
		List<String> infos = new ArrayList<String>();
		String line = "";
		
		while ((line = numberReader.readLine()) != null) {
			infos.add(this.spellName(line, "Key:"));
		}
		
		for (String s : infos) {
			final KeyData data = new KeyData(s);
			final String encode = data.encrypt(s);
			writer.write("Key: " + s + " , " + "Rebof: " + encode + System.lineSeparator());
			this.rebofs.add(encode);
		}
		
		numberReader.close();
		writer.close();
	}
	
	public void getFilesToRebofToken(String outpath) throws Exception {
		File file = new File(outpath);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		
		for (String s : this.rebofs) {
			writer.write(s + ",");
		}
		
		writer.close();
	}
	
	public String spellName(String name, String format) {
		name = name.trim();
		name = name.replace(format, "");
		name = name.trim();
		return name;
	}
}
