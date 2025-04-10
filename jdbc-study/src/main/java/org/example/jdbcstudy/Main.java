package org.example.jdbcstudy;

import org.example.jdbcstudy.entities.StockEntity;
import org.example.jdbcstudy.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    private static StockEntity[] retrieveStockData() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient(); // 요청(Request)과 응답(Response) 객체를 담을 컨테이너 객체
        HttpRequest request = HttpRequest.newBuilder() // 요청(Request)에 대한 정보를 담을 객체
                .uri(new URI("https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo?serviceKey=zLLw9vnuyP4OtHI2Ol7GNM2xx6%2FZ0ylj0Rl%2FjR8mBPHTbP0uGOFw4qcP5aF4C8fGjNS4PnhUPTd5bIMe8yBgtw%3D%3D&numOfRows=5000&pageNo=1&resultType=json"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // 응답(Response)에 대한 정보를 담을 객체
        String responseBody = response.body(); // 응답 본문 문자열
        JSONArray items = new JSONObject(responseBody)
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");
        // items가 가지는 각 JsonObject의 basDt가 달라지는 점으 ㄹ확인. 달라지는 지점에서는 과거 데이터이고 여기서부터는 다시 첫 종목부터 사이클이 도는 점을 확인.
        // 고로 첫 인자의 basDt와 다른 값을 가지는 지점부터는 사용할 필요가 없음.
        String mostRecentBasDt = items.getJSONObject(0).getString("basDt");
        int thresholdIndex = items.length() - 1;
        for (int i = 0; i < items.length(); i++) {
            JSONObject object = items.getJSONObject(i);
            if (!object.getString("basDt").equals(mostRecentBasDt)) {
                thresholdIndex = i - 1;
                break;
                // basDt 값이 다르면 현재 인덱스 -1까지만 사용하면 됨.
            }
        }
        System.out.println("thresholdIndex: " + thresholdIndex);

        StockEntity[] stocks = new StockEntity[thresholdIndex + 1];
        for (int i = 0; i < stocks.length; i++) {
            JSONObject object = items.getJSONObject(i);
            String id = object.getString("srtnCd");
            String isin = object.getString("isinCd");
            String market = object.getString("mrktCtg");
            String displayText = object.getString("itmsNm");

            StockEntity stock = new StockEntity();
            stock.setId(id);
            stock.setIsin(isin);
            stock.setMarket(market);
            stock.setDisplayText(displayText);
            stock.setCreatedAt(LocalDateTime.now());
            stocks[i] = stock;
        }
        return stocks;
    }

    private static void deleteStockById(Connection connction, String id) throws SQLException {
        // 전달 받은 id 로 레코드를 삭제하는 메서드 ㄱ
        PreparedStatement stmt = connction.prepareStatement("DELETE FROM `jdbc_study`.`stocks` WHERE `id` = ?");
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    private static void insertStocks(Connection connection, StockEntity[] stocks) throws SQLException {
        int lastProgress = 0;
        int done = 0;

        for (StockEntity stock : stocks) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `jdbc_study`.`stocks` (`id`, `isin`, `market`, `display_text`, `created_at`) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, stock.getId());
            stmt.setString(2, stock.getIsin());
            stmt.setString(3, stock.getMarket());
            stmt.setString(4, stock.getDisplayText());
            stmt.setTimestamp(5, Timestamp.valueOf(stock.getCreatedAt()));
            stmt.executeUpdate(); // INSERT UPDATE DELETE
            done++;

            int progress = done * 100 / stocks.length;
            if (progress != lastProgress) {
                System.out.println(progress + "%");
            }
            lastProgress = progress;
        }
    }

    public static void main(String[] args) {
        StockEntity[] stocks;
        try {
            System.out.println("- 주식 정보 받아오기 시작");
            stocks = retrieveStockData();
            System.out.println("- 주식 정보 받아오기 성공");
        } catch (URISyntaxException | InterruptedException | IOException e) {
            System.out.println("- 주식 정보 받아오기 실패");
            e.printStackTrace();
            return;
        }
        Connection connection;
        try {
            System.out.println("- 데이타베이스 연결 시작");
            connection = DatabaseUtils.getConnection();
            System.out.println("- 데이타베이스 연결 성공");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("- 데이타베이스 연결 실패");
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("- 주식 정보 삽입 시작");
            insertStocks(connection, stocks);
            System.out.println("- 주식 정보 삽입 성공");
        } catch (SQLException e) {
            System.out.println("- 주식 정보 삽입 실패");
            e.printStackTrace();
            return;
        }

        try {
            deleteStockById(connection, "A016790");
            System.out.println("- id: A016790 자료 건 삭제완료");
        } catch (SQLException e) {
            System.out.println("- id: A016790 자료 건 삭제실패");
            throw new RuntimeException(e);
        }
    }
}
