package twitter_kols;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import twitter_kols.core.ProcessData;
import twitter_kols.core.graph.Graph;
import twitter_kols.core.graph.GraphBuilder;
import twitter_kols.core.graph.GraphVisualizer;
import twitter_kols.core.graph.PageRank;
import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.core.handlers.LogoutRequestHandler;
import twitter_kols.webdriver.AbstractDriverManager;
import twitter_kols.webdriver.ChromeDriverManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
//        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
//        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
//        loginRequestHandler.handleRequest();
//        ProcessData processData = new ProcessData();
//        processData.processData();
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
//        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
//        logoutRequestHandler.handleRequest();
//        ChromeDriverManager.closeDriver();
    }
}