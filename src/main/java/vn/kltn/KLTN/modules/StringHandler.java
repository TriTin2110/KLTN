package vn.kltn.KLTN.modules;

import java.text.Normalizer;

//Hàm này biến một chuỗi đầu vào (có dấu, có khoảng trắng thừa, có chữ hoa...)
//thành chuỗi không dấu, viết thường, gọn gàng
public class StringHandler {
	public static String handlingString(String raw) {
		if (raw == null || raw.isBlank())
			return null;
		/*- Tách ký tự có dấu thành chữ cái cơ bản + ký tự dấu riêng biệt (Normalizer.normalize(raw, Normalizer.Form.NFD)
		 * "\\p{InCombiningDiacriticalMarks}+" Biểu thức chính quy này xóa tất cả các ký tự dấu (dấu thanh, dấu mũ, chấm, móc...) mà Unicode tách*/
		String result = Normalizer.normalize(raw, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+",
				"");
		return result.trim().toLowerCase(); // Thay thế các khoảng trống = " "
	}
}
