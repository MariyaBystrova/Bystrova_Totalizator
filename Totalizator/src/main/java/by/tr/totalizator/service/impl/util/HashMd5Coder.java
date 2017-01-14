package by.tr.totalizator.service.impl.util;

import org.apache.commons.codec.digest.DigestUtils;

public class HashMd5Coder {

	public static String hashMd5(byte[] text){
		return DigestUtils.md5Hex(text);
	}
	
	public static String hashMd5(String text) {
		return DigestUtils.md5Hex(text);
	}

}
