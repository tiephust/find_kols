package twitter_kols.core;

import twitter_kols.core.find_users.FindFlowers;
import twitter_kols.utils.FileWriters;

import java.util.List;
import java.util.Map;
import java.util.Set;

//Lấy các thông số đánh giá xếp hạng KOLs
public class ProcessData {
    private FindFlowers findFlowers;

    private Map<String, List<String>> mapKolsFlowers;

    public ProcessData() {
        this.findFlowers = new FindFlowers();
    }
    public void processData(Set<String> listKolsUrl) {
        for (String kolUrl : listKolsUrl) {
            String urlList = kolUrl.replace("\"", "").trim();
            String urlListFollowers = urlList + "/followers";
            List<String> flowers = findFlowers.findListUsers(urlListFollowers);
            String urlListVerifiedFollowers = urlList + "/verified_followers";
            List<String> verifiedFollowers = findFlowers.findListUsers(urlListVerifiedFollowers);
            String urlListFollowing = urlList + "/following";
            List<String> following = findFlowers.findListUsers(urlListFollowing);
            FileWriters.writeResultsToCSV("result.csv", kolUrl, flowers, verifiedFollowers, following);
        }
        System.out.println("Map of KOLs and their flowers: " + mapKolsFlowers);
    }

    public Map<String, List<String>> getMapKolsFlowers() {
        return mapKolsFlowers;
    }

}
