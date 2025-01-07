package twitter_kols.core;

import org.json.JSONArray;
import twitter_kols.core.graph.Graph;
import twitter_kols.core.graph.GraphBuilder;
import twitter_kols.core.graph.GraphVisualizer;
import twitter_kols.core.graph.PageRank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class MainPageRank {
    public static void main(String[] args) throws InterruptedException, IOException {
        String filePath = "result.json";
        JSONArray jsonArray = new JSONArray(new String(Files.readAllBytes(Paths.get(filePath))));
        Graph graph = new Graph();
        GraphBuilder graphBuilder = new GraphBuilder(graph);
        graphBuilder.buildGraphFromJSON(jsonArray);
        GraphVisualizer graphVisualizer = new GraphVisualizer(graph);
        graphVisualizer.visualize();
        PageRank pageRank = new PageRank();
        Map<String, Double> pageRankMap = pageRank.calculatePageRank(jsonArray);
        pageRank.printSortedPageRank(pageRankMap);
    }
}
