package twitter_kols.utils;

import com.opencsv.CSVWriter;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Lớp FileWriters cung cấp các phương thức hỗ trợ ghi dữ liệu vào file CSV và JSON.
 *
 * Chức năng chính:
 * - Thêm liên kết vào file CSV trong thư mục `userlink`.
 * - Ghi kết quả (người theo dõi, người theo dõi đã xác minh, và danh sách đang theo dõi) vào file CSV.
 * - Ghi dữ liệu JSON vào file.
 *
 * Thư viện sử dụng:
 * - OpenCSV: Ghi dữ liệu vào file CSV.
 * - org.json: Ghi dữ liệu JSON.
 */
public class FileWriters {

    /**
     * Hàm khởi tạo mặc định.
     */
    public FileWriters() {
    }

    /**
     * Phương thức thêm danh sách liên kết vào file CSV trong thư mục `userlink`.
     * Nếu thư mục chưa tồn tại, nó sẽ được tạo mới.
     *
     * @param listLink Danh sách liên kết cần thêm.
     * @param keyword  Tên tệp CSV sẽ được sử dụng (không bao gồm "UsersLinks").
     */
    public void appendLinksToCSV(Set<String> listLink, String keyword) {
        // Tạo thư mục 'userlink' nếu chưa tồn tại
        File directory = new File("userlink");
        if (!directory.exists()) {
            directory.mkdir(); // Tạo thư mục mới nếu chưa có
        }

        // Đặt tên tệp CSV dựa trên từ khóa
        String fileName = "userlink/" + keyword + ".csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) { // Mở file ở chế độ thêm (append)
            for (String element : listLink) {
                String[] record = {element}; // Mỗi liên kết là một dòng trong file CSV
                writer.writeNext(record); // Ghi dữ liệu vào tệp
            }
            System.out.println("Set appended to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage()); // Thông báo lỗi khi ghi file
        }
    }

    /**
     * Phương thức ghi kết quả (người theo dõi, người theo dõi đã xác minh, danh sách đang theo dõi) vào file CSV.
     *
     * @param filePath         Đường dẫn tệp CSV.
     * @param link             Liên kết đến người dùng.
     * @param followers        Danh sách người theo dõi.
     * @param verifiedFollowers Danh sách người theo dõi đã xác minh.
     * @param following        Danh sách đang theo dõi.
     */
    public static void writeResultsToCSV(String filePath, String link, List<String> followers, List<String> verifiedFollowers, List<String> following) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // Mở file ở chế độ thêm
            // Lấy tên người dùng từ liên kết (e.g., "https://x.com/Bitcoin" -> "Bitcoin")
            String username = link.substring(link.lastIndexOf("/") + 1);

            // Chuẩn bị một dòng dữ liệu với các cột theo định dạng CSV
            String row = String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    username, // Cột đầu tiên là tên người dùng
                    String.join(", ", followers), // Danh sách người theo dõi
                    String.join(", ", verifiedFollowers), // Danh sách người theo dõi đã xác minh
                    String.join(", ", following)); // Danh sách đang theo dõi

            // Ghi dòng dữ liệu vào file CSV
            bw.write(row);
        } catch (IOException e) {
            e.printStackTrace(); // Thông báo lỗi khi ghi file
        }
    }

    /**
     * Phương thức ghi dữ liệu JSON vào tệp với định dạng đẹp (indentation).
     *
     * @param filePath  Đường dẫn tệp JSON.
     * @param jsonArray Dữ liệu cần ghi dưới dạng JSONArray.
     */
    public static void writeToJSONFile(String filePath, JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(filePath)) { // Mở tệp JSON để ghi
            fileWriter.write(jsonArray.toString(4)); // Ghi dữ liệu JSON với thụt lề (4 spaces)
            System.out.println("Data successfully saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to JSON file: " + filePath); // Thông báo lỗi khi ghi file
            e.printStackTrace();
        }
    }
}
