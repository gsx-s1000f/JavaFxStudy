package application.util;

public class Native2Ascii {
	final static String PREFIX = "\\u";
	final static String CRLF = "\\r\\n"; 
	final static String CR = "\\r"; 
	final static String LF = "\\n"; 
	
	final static String RG_RETURN = "\\\\ua"; 
	final static String RG_PREFIX = "\\\\u"; 
	
	public static String toAscii(String value) {
		if (value == null || value.length() < 1) {
			return value;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : value.toCharArray()) {
			String code = Integer.toHexString(c);
			System.out.println(c + "=" + code);
			if(code.equals("a")) {
				sb.append(c);	// 改行の場合は変換しない
			}else {
				sb.append("\\u").append(code);
			}
		}
		return sb.toString();
	}
	public static String toNative(String value) {
		if (value == null || value.length() < 1) {
			return value;
		}
		// 改行コードも含めてすべてasciiにしてから分解する。
		String tmp = value.replaceAll(CRLF, RG_RETURN).replaceAll(CR, RG_RETURN).replaceAll(LF, RG_RETURN);
		System.out.println(tmp);
		StringBuilder sb = new StringBuilder();
		for (String code : tmp.split(RG_PREFIX)) {
			if(code.length() < 1) {
				continue;
			}
			sb.append((char)Integer.parseInt(code, 16));
		}
		return sb.toString();
	}
}
