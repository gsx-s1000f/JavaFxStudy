package application.util;

public class Native2AsciiTest {
	public static void main(String[] args) {
		String a = "アメンボ赤い\r\nなあいうえお";
		a = Native2Ascii.toAscii(a);
		System.out.println(a);
		a = Native2Ascii.toNative(a);
		System.out.println(a);
	}

}
