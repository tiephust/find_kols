package twitter_kols.utils.csv;

import com.opencsv.CSVWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FileWriters {

    public FileWriters() {
    }

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

    public static void writeToJSONFile(String filePath, JSONObject jsonArray) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonArray.toString(4)); // Ghi JSON với thụt lề 4 spaces
            System.out.println("Dữ liệu đã được lưu vào " + filePath);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào file JSON: " + filePath);
            e.printStackTrace();
        }
    }
}
