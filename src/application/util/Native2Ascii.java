package application.util;

public class Native2Ascii {
	/** UTF8文字コードプレフィクス */
	final static String PREFIX = "\\u";
	/** 改行コード */
	final static String CRLF = "\\r\\n"; 
	final static String CR = "\\r"; 
	final static String LF = "\\n"; 
	
	/** 改行にマッチするascii正規表現パターン */
	final static String RG_RETURN = "\\\\ua";
	/** UTF8文字コードプレフィクスの正規表現パターン */
	final static String RG_PREFIX = "\\\\u"; 
	
	/**
	 * Native2Ascii
	 * @param value	変換対象文字列
	 * @return	Asciiに置き換えた文字列（ただし改行コードは`\ua`に変換しない。
	 */
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
	/**
	 * Ascii2Native
	 * @param value	変換対象文字列
	 * @return	Nativeに変換した文字列（`\ua`は改行コードに戻る）
	 */
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
				continue;	// 空文字は飛ばす（`\u\u`など）
			}
			sb.append((char)Integer.parseInt(code, 16));
		}
		return sb.toString();
	}
}
