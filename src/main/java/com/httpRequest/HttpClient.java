package com.httpRequest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    public static void main(String[] args) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("user1", "Ivan Ivanov");
        params.put("user2", "Petr Petrov");

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json");

        String body = "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                "<HTML>\n" +
                "   <HEAD>\n" +
                "      <TITLE>\n" +
                "         Title \n" +
                "      </TITLE>\n" +
                "   </HEAD>\n" +
                "<BODY>\n" +
                "   <P>Hello World!</P> \n" +
                "</BODY>\n" +
                "</HTML>";

        System.out.println("GET request");
        HttpResponse<JsonNode> response = executeRequest("http://httpbin.org/anything", "GET", params, headers, null);
        printResponse(response);

        System.out.println("\n\nPOST request");
        response = executeRequest("http://httpbin.org/post", "POST", params, headers, body);
        printResponse(response);

        Unirest.shutdown();
    }

    private static void printResponse(HttpResponse<JsonNode> response) {
        System.out.println("\nStatus: " + response.getStatus() + " " + response.getStatusText());
        System.out.println("\nHeaders:");
        response.getHeaders().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println();
        System.out.println("Body:\n" + response.getBody());
    }

    private static HttpResponse<JsonNode> executeRequest(
            String uri
            , String method
            , Map<String, Object> params
            , Map<String, String> headers
            , String body) {
        try {
            if (body == null) {
                body = "";
            }
            switch (method.toLowerCase()) {
                case "delete":
                    return Unirest.delete(uri)
                            .queryString(params)
                            .headers(headers)
                            .body(body)
                            .asJson();
                case "get":
                    return Unirest.get(uri)
                            .queryString(params)
                            .headers(headers)
                            .asJson();
                case "head":
                    return Unirest.head(uri)
                            .queryString(params)
                            .headers(headers)
                            .asJson();
                case "options":
                    return Unirest.options(uri)
                            .queryString(params)
                            .headers(headers)
                            .body(body)
                            .asJson();
                case "path":
                    return Unirest.patch(uri)
                            .queryString(params)
                            .headers(headers)
                            .body(body)
                            .asJson();
                case "post":
                    return Unirest.post(uri)
                            .queryString(params)
                            .headers(headers)
                            .body(body)
                            .asJson();
                case "put":
                    return Unirest.put(uri)
                            .queryString(params)
                            .headers(headers)
                            .body(body)
                            .asJson();
                default:
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Invalid method name");
    }
}
