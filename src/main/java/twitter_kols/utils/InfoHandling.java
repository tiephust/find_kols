package twitter_kols.utils;

public class InfoHandling {

    /**
     * Phương thức này dùng để trích xuất ID của bài đăng từ liên kết.
     * Giả sử ID là phần cuối cùng của URL.
     *
     * @param link Liên kết chứa ID của bài đăng.
     * @return ID của bài đăng dưới dạng chuỗi.
     */
    public static String extractPostId(String link) {
        // Trích xuất phần sau dấu '/' trong liên kết, đó là ID bài đăng
        return link.substring(link.lastIndexOf("/") + 1);
    }

    /**
     * Phương thức này chuyển một chuỗi số có thể chứa hậu tố "K" hoặc "M" (nghĩa là nghìn và triệu) thành số nguyên.
     *
     * @param numStr Chuỗi số cần chuyển đổi.
     * @return Giá trị số nguyên tương ứng với chuỗi.
     */
    public static int parseNumber(String numStr) {
        // Nếu chuỗi rỗng hoặc null, trả về 0
        if (numStr == null || numStr.isEmpty()) {
            return 0;
        }

        // Loại bỏ dấu phẩy nếu có trong chuỗi
        numStr = numStr.replace(",", "");

        // Kiểm tra xem chuỗi có kết thúc bằng "K" hay "M"
        if (numStr.endsWith("K")) {
            // Nếu có hậu tố "K", chuyển đổi giá trị thành nghìn
            return (int) (Double.parseDouble(numStr.replace("K", "")) * 1_000);
        } else if (numStr.endsWith("M")) {
            // Nếu có hậu tố "M", chuyển đổi giá trị thành triệu
            return (int) (Double.parseDouble(numStr.replace("M", "")) * 1_000_000);
        } else {
            // Nếu không có hậu tố, trực tiếp chuyển chuỗi thành số nguyên
            return Integer.parseInt(numStr);
        }
    }
}
