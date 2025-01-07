package twitter_kols.utils.csv;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriters {

    // Hàm thêm dữ liệu vào file JSON
    public static void appendToJSONFile(String filePath, JSONObject newData) {
        try {
            // Đọc dữ liệu hiện có từ file JSON
            File file = new File(filePath);
            JSONArray jsonArray;

            if (file.exists() && file.length() > 0) {
                // Nếu file đã có dữ liệu, đọc nó vào JSONArray
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                jsonArray = new JSONArray(content);
            } else {
                // Nếu file chưa có dữ liệu, tạo mảng JSON mới
                jsonArray = new JSONArray();
            }

            // Thêm dữ liệu mới vào mảng
            jsonArray.put(newData);

            // Ghi lại mảng JSON vào file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(jsonArray.toString(4)); // Viết với định dạng dễ đọc (4 là số khoảng trắng)
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm ghi dữ liệu vào file JSON
    public static void writeToJSONFile(String filePath, JSONArray data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data.toString(4)); // Viết dữ liệu với định dạng dễ đọc
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
