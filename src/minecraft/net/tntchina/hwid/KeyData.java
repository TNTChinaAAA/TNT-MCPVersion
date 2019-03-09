package net.tntchina.hwid;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class KeyData {

	/** �ַ���Ĭ�ϼ�ֵ */
	public static String strDefaultKey = "inventec2017@#$%^&";
	/** ���ܹ��� */
	public Cipher encryptCipher = null;
	/** ���ܹ��� */
	public Cipher decryptCipher = null;

	public KeyData(String strKey) throws Exception {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		this.encryptCipher = Cipher.getInstance("DES");
		this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		this.decryptCipher = Cipher.getInstance("DES");
		this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 
	 * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813����public static byte[]
	 * 
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת������
	 * 
	 * @param arrB ��Ҫת����byte����
	 * 
	 * @return ת������ַ���
	 * 
	 * @throws Exception �������������κ��쳣�������쳣ȫ���׳�
	 * 
	 */

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// ÿ��byte��2���ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�2��
		StringBuffer sb = new StringBuffer(iLen * 2);

		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����

			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}

			sb.append(Integer.toString(intTmp, 16));
		}

		return sb.toString();

	}

	/**
	 * 
	 * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬��public static String byteArr2HexStr(byte[] arrB)
	 * 
	 * ��Ϊ�����ת������
	 * 
	 * @param strIn ��Ҫת�����ַ���
	 * 
	 * @return ת�����byte����
	 * 
	 */

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
		byte[] arrOut = new byte[iLen / 2];

		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}

		return arrOut;

	}

	/**
	 * 
	 * �����ֽ�����
	 * 
	 * @param arrB ����ܵ��ֽ�����
	 * 
	 * @return ���ܺ���ֽ�����
	 * 
	 */

	public byte[] encrypt(byte[] arrB) throws Exception {
		return this.encryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 * �����ַ���
	 * 
	 * @param strIn ����ܵ��ַ���
	 * 
	 * @return ���ܺ���ַ���
	 * 
	 */

	public String encrypt(String strIn) throws Exception {
		return KeyData.byteArr2HexStr(this.encrypt(strIn.getBytes()));
	}

	/**
	 * 
	 * �����ֽ�����
	 * 
	 * @param arrB ����ܵ��ֽ�����
	 * 
	 * @return ���ܺ���ֽ�����
	 * 
	 */

	public byte[] decrypt(byte[] arrB) throws Exception {
		return this.decryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 * �����ַ���
	 * 
	 * @param strIn ����ܵ��ַ���
	 * 
	 * @return ���ܺ���ַ���
	 * 
	 */

	public String decrypt(String strIn) throws Exception {
		return new String(this.decrypt(KeyData.hexStr2ByteArr(strIn)));
	}

	/**
	 * 
	 * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
	 * 
	 * @param arrBTmp ���ɸ��ַ������ֽ�����
	 * 
	 * @return ���ɵ���Կ
	 * 
	 */

	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];
		// ��ԭʼ�ֽ�����ת��Ϊ8λ

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// ������Կ
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}
}