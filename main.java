import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:History";
            Connection conn = DriverManager.getConnection(url);

            String sql = "SELECT title, visit_count, typed_count, last_visit_time FROM urls LIMIT 30;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<String> titles = new ArrayList<>();
            int totalTyped = 0;
            int totalVisits = 0;
            long latestTime = 0;

            // 🔥 NEW: behavior counters
            int suspiciousCount = 0;
            int accountCount = 0;
            int socialCount = 0;
            int researchCount = 0;

            while (rs.next()) {
                String title = rs.getString("title");
                int visits = rs.getInt("visit_count");
                int typed = rs.getInt("typed_count");
                long chromeTime = rs.getLong("last_visit_time");

                long unixTime = (chromeTime / 1000000) - 11644473600L;

                if (unixTime > latestTime) {
                    latestTime = unixTime;
                }

                if (title != null && !title.isEmpty()) {
                    titles.add(title);

                    String lowerTitle = title.toLowerCase();

                    // 🔴 Suspicious keywords
                    if (lowerTitle.contains("hack") || lowerTitle.contains("exploit") ||
                        lowerTitle.contains("bypass") || lowerTitle.contains("crack") ||
                        lowerTitle.contains("phishing") || lowerTitle.contains("sql injection") ||
                        lowerTitle.contains("ddos") || lowerTitle.contains("tracker")) {
                        suspiciousCount++;
                    }

                    // 🟡 Account / security
                    if (lowerTitle.contains("login") || lowerTitle.contains("account") ||
                        lowerTitle.contains("password") || lowerTitle.contains("verification")) {
                        accountCount++;
                    }

                    // 🔵 Social
                    if (lowerTitle.contains("youtube") || lowerTitle.contains("netflix") ||
                        lowerTitle.contains("instagram") || lowerTitle.contains("tiktok")) {
                        socialCount++;
                    }

                    // 🟢 Research
                    if (lowerTitle.contains("how to") || lowerTitle.contains("tutorial") ||
                        lowerTitle.contains("guide") || lowerTitle.contains("stack overflow")) {
                        researchCount++;
                    }
                }

                totalTyped += typed;
                totalVisits += visits;
            }

            java.util.Date lastVisit = new java.util.Date(latestTime * 1000);

            // 🟢 OUTPUT
            System.out.println("\n========== BROWSER ANALYSIS ==========");
            System.out.println("Top Sites: " + titles.subList(0, Math.min(5, titles.size())));
            System.out.println("Total Typed URLs: " + totalTyped);
            System.out.println("Average Visits: " + (totalVisits / 30.0));
            System.out.println("Most Recent Activity: " + lastVisit);

            System.out.println("\n========== BEHAVIOR PROFILE ==========");
            System.out.println(generateProfile(totalTyped, totalVisits, suspiciousCount, accountCount, socialCount, researchCount));

            conn.close();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // 🔥 NEW SMART PROFILE LOGIC
    public static String generateProfile(int typed, int visits, int suspicious, int account, int social, int research) {

        if (suspicious >= 3 && typed > 2) {
            return "HIGH RISK: User shows repeated interaction with suspicious technical content and deliberate search behavior.";
        } 
        else if (suspicious >= 1) {
            return "MEDIUM RISK: User shows some investigative or potentially suspicious browsing patterns.";
        } 
        else if (account > 5) {
            return "User frequently interacts with account and authentication systems, indicating sensitive activity.";
        } 
        else if (social > 5) {
            return "User behavior is primarily social and entertainment-based, indicating normal usage.";
        } 
        else if (research > 5) {
            return "User demonstrates research-focused behavior with frequent learning-related searches.";
        } 
        else {
            return "User activity is mixed with no strong behavioral pattern detected.";
        }
    }
}