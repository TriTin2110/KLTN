package vn.kltn.KLTN.modules;

public class StringHandler {
	public static String handlingString(String raw) {
		return raw.trim().toLowerCase().replaceAll("\\s+", " "); // Thay thế các khoảng trống = " "
	}
}
