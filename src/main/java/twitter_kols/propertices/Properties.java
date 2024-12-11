package twitter_kols.propertices;

import java.util.logging.Logger;

public enum Properties {
    // Các giá trị cần lấy từ file cấu hình twitter.properties
    WEBSITE_URL("WEBSITE_URL"),
    LOGIN_URL("LOGIN_URL"),
    LOGOUT_URL("LOGOUT_URL"),
    USERNAME("USERNAME"),
    PASSWORD("PASSWORD"),
    EMAIL("EMAIL"),
    KEYWORD("KEYWORD"),
    TEST("TEST"),
    LIKE_LIMIT("LIKE_LIMIT"),
    TWITTER_FEED_URL("TWITTER_FEED_URL"),
    DELAY_IN_MS("DELAY_IN_MS"),
    REPEAT_AFTER_IN_MINUTES("REPEAT_AFTER_IN_MINUTES");

    // Logger để ghi lại thông báo lỗi hoặc cảnh báo
    public static final Logger logger = Logger.getLogger(Properties.class.getName());

    // Khai báo đối tượng chứa các cặp key-value từ file cấu hình
    public static final java.util.Properties twitterProperties = new java.util.Properties();

    // Tên của file cấu hình
    public static final String PROPERTIES_FILE = "twitter.properties";

    // Khối static để tải file cấu hình khi ứng dụng khởi động
    static {
        try {
            // Tải file cấu hình từ classpath
            twitterProperties.load(Properties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (Exception e) {
            // Nếu có lỗi khi tải file, ghi lại thông báo cảnh báo
            logger.warning("Failed to load Twitter Properties file : " + e.getMessage());
        }
    }

    // Biến khóa để lưu tên của các thuộc tính cần lấy từ file cấu hình
    private final String key;

    // Constructor để gán giá trị cho biến khóa
    Properties(String key) {
        this.key = key;
    }

    // Phương thức trả về giá trị của thuộc tính từ file cấu hình
    public String val() {
        // Lấy giá trị của thuộc tính từ file cấu hình, nếu không có thì trả về chuỗi rỗng
        return twitterProperties.getProperty(key, "");
    }
}
