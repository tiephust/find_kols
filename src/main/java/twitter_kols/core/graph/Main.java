package twitter_kols.core.graph;

import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.core.find_users.FindFlowers;
import twitter_kols.core.find_users.FindKolsUrl;
import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.utils.FileReader;
import twitter_kols.webdriver.ChromeDriverManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();
        loginRequestHandler.login();
        FindKolsUrl findKols = new FindKolsUrl();
        findKols.findKols();
        Set<String> kols = findKols.getListKoks();
        FindFlowers findFlowers = new FindFlowers();
        findFlowers.findMapKolsFlowers(kols);
        try {
            // Initialize components
            FileReader fileReader = new FileReader();
            Graph graph = new Graph();
            GraphBuilder graphBuilder = new GraphBuilder(graph);

            // File paths
            String csvFile = "E:\\find_kols\\result.csv";
            String jsonFile = "E:\\find_kols\\all.json";

            // Read CSV and build graph
            List<CSVRecord> csvRecords = fileReader.readCSV(csvFile);
            graphBuilder.buildGraphFromCSV(csvRecords);

            // Read JSON and build graph
            JSONObject jsonObject = fileReader.readJSON(jsonFile);
            graphBuilder.buildGraphFromJSON(jsonObject);
            
            
//            System.out.println(graph.getNodeCount());

            // Visualize graph or compute PageRank
//            System.out.println("Graph structure: " + graph.getGraph());
            
//         // Step 3: Compute PageRank
            PageRank pageRank = new PageRank(graph);
            Map<String, Double> pageRankScores = pageRank.calculatePageRank();
//
            // Step 4: Display the results
            System.out.println("PageRank Scores:");
            for (Map.Entry<String, Double> entry : pageRankScores.entrySet()) {
                System.out.println("Node: " + entry.getKey() + ", PageRank: " + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
