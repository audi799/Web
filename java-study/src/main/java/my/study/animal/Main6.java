package my.study.animal;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class Main6 {
    public static void main(String[] args) {
        try {
//            System.out.println(sendGetRequest("https://api.ipify.org?format=json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String sendGetRequest(String url) throws IOException, InterruptedException {
//        HttpClient client = httpClient.newHttpClient();
//        httpRequest request = HttpRequest.newBuilder()
//                .url(URI.create(url))
//                .GET()
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
}
