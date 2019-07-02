package com.cyl.transaction;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ConcurrencyTest {
    private static final int loop = 100;

    public static void main(String[] args) throws IOException, InterruptedException {
//        purchase();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < loop; i++) {
                try {
                    purchase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < loop; i++) {
                try {
                    purchase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < loop; i++) {
                try {
                    purchase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
//        thread3.start();
    }

    private static void purchase() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/v0/purchase");
        System.out.println("create http post request done");
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            String returnStr = EntityUtils.toString(response.getEntity());
            System.out.println("get http post result:" + returnStr);
        } finally {
            response.close();
        }
    }
}
