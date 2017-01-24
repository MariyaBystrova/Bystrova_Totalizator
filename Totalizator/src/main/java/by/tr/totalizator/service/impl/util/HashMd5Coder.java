package by.tr.totalizator.service.impl.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Represents a class for making a hash value using
 * {@link org.apache.commons.codec.digest.DigestUtils}.
 * 
 * @author Mariya Bystrova
 *
 */
public class HashMd5Coder {

	/**
	 * Returns a hashed String value of an input parameter.
	 * 
	 * @param text
	 *            a byte[] value to be hashed.
	 * @return a hashed String value of an input parameter.
	 */
	public static String hashMd5(byte[] text) {
		return DigestUtils.md5Hex(text);
	}

	/**
	 * Returns a hashed String value of an input parameter.
	 * 
	 * @param text
	 *            a String value to be hashed.
	 * @return a hashed String value of an input parameter.
	 */
	public static String hashMd5(String text) {
		return DigestUtils.md5Hex(text);
	}

}
