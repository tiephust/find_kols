package twitter_kols.utils;

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

/**
 * Lớp FileReader cung cấp các phương thức hỗ trợ đọc dữ liệu từ các định dạng file phổ biến.
 *
 * Chức năng chính:
 * - Đọc file CSV và trả về danh sách các record (hàng).
 * - Đọc file JSON và chuyển đổi nội dung thành đối tượng JSONObject.
 * - Đọc danh sách liên kết từ file CSV.
 * - Đọc dữ liệu từ file CSV và trả về dưới dạng danh sách các mảng chuỗi.
 *
 * Thư viện sử dụng:
 * - Apache Commons CSV: Xử lý file CSV.
 * - org.json: Xử lý file JSON.
 */
public class FileReader {

    /**
     * Đọc một file CSV và trả về dữ liệu dưới dạng danh sách các hàng (records).
     *
     * @param csvFile Đường dẫn tới file CSV.
     * @return Danh sách các record (hàng) trong file CSV.
     * @throws IOException Nếu có lỗi khi đọc file.
     */
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

    /**
     * Đọc một file JSON và trả về nội dung dưới dạng đối tượng JSONObject.
     *
     * @param jsonFile Đường dẫn tới file JSON.
     * @return Đối tượng JSONObject chứa dữ liệu từ file JSON.
     * @throws IOException Nếu có lỗi khi đọc file.
     */
    public JSONObject readJSON(String jsonFile) throws IOException {
        // Đọc toàn bộ nội dung file JSON vào một chuỗi
        String content = new String(Files.readAllBytes(Paths.get(jsonFile)));

        // Chuyển đổi chuỗi JSON thành đối tượng JSONObject
        return new JSONObject(content);
    }

    /**
     * Đọc danh sách liên kết từ một file CSV và trả về dưới dạng danh sách chuỗi.
     *
     * @param filePath Đường dẫn tới file CSV.
     * @return Danh sách các liên kết đọc từ file CSV.
     */
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

    /**
     * Đọc dữ liệu từ một file CSV và trả về dưới dạng danh sách các mảng chuỗi.
     *
     * @param filePath Đường dẫn tới file CSV.
     * @return Danh sách các hàng trong file CSV dưới dạng mảng chuỗi.
     */
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
