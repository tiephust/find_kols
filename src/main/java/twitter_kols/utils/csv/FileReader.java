package twitter_kols.utils.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public List<CSVRecord> readCSV(String csvFile) throws IOException {
        // Tạo đối tượng Reader để đọc nội dung file CSV
        Reader reader = Files.newBufferedReader(Paths.get(csvFile));

        // Sử dụng CSVParser để phân tích cú pháp của file CSV với dòng đầu tiên làm header
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

        // Lấy danh sách các record từ file CSV
        List<CSVRecord> records = new ArrayList<>(csvParser.getRecords());

        // Đóng CSVParser sau khi sử dụng
        csvParser.close();

        // Trả về danh sách các record
        return records;
    }

    public JSONObject readJSON(String jsonFile) throws IOException {
        // Đọc toàn bộ nội dung file JSON vào một chuỗi
        String content = new String(Files.readAllBytes(Paths.get(jsonFile)));

        // Chuyển đổi chuỗi JSON thành đối tượng JSONObject
        return new JSONObject(content);
    }

    public static List<String> readLinksFromCSV(String filePath) {
        // Danh sách để lưu trữ các liên kết
        List<String> links = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;

            // Đọc từng dòng từ file CSV và thêm vào danh sách
            while ((line = br.readLine()) != null) {
                links.add(line);
            }
        } catch (IOException e) {
            // Xử lý lỗi khi đọc file
            e.printStackTrace();
        }
        return links;
    }

    public static List<String[]> readCSV1(String filePath) {
        // Danh sách để lưu trữ các hàng
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;

            // Đọc từng dòng từ file CSV và tách các cột bằng dấu phẩy
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            // Xử lý lỗi khi đọc file
            e.printStackTrace();
        }
        return rows;
    }
}
