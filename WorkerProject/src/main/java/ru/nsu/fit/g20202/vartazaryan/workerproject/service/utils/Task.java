package ru.nsu.fit.g20202.vartazaryan.workerproject.service.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.paukov.combinatorics3.Generator;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.ResponseDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Task implements Runnable
{
    private String ticketID;
    private int start;
    private int finish;
    private int maxLen;
    private String hash;
    private List<String> alphabet;

    private String res;
    private final ObjectMapper objectMapper;

    private int iterations = 0;

    public Task(TaskDTO dto)
    {
        this.ticketID = dto.getTicketID();
        this.start = dto.getStart();
        this.finish = dto.getFinish();
        this.maxLen = dto.getMaxLen();
        this.hash = dto.getHash();

        alphabet = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8","9");
        objectMapper = new ObjectMapper();

        System.out.println("Task info:");
        System.out.println("Start = "+dto.getStart());
        System.out.println("Finish = "+dto.getFinish());
        System.out.println("MaxLen = "+dto.getMaxLen());
    }

    @Override
    public void run()
    {
        Generator.permutation(alphabet).withRepetitions(maxLen).stream()
                .skip(start)
                .limit(finish)
                .forEach(this::calcHash);

        System.out.println("Execution finished. Total iterations = "+this.iterations+"Sending result...");

        String managerHost = "http://manager:8080/internal/api/manager/hash/crack/request";
        URI uri = URI.create(managerHost);
        ResponseDTO dto = ResponseDTO.builder()
                .result(this.res)
                .ticketID(this.ticketID)
                .build();

        HttpClient managerClient = HttpClient.newHttpClient();
        try {
            HttpRequest taskResponse = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                    .build();

            HttpResponse<String> response = managerClient.send(taskResponse, HttpResponse.BodyHandlers.ofString());
            System.out.println("Result was sent!");

            if (response.statusCode() != 200)
            {
                System.out.println("Something went wrong on manager side. Status code: "+response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void calcHash(List<String> wordList)
    {
        iterations++;

        StringBuilder word = new StringBuilder();
        for (String s : wordList)
            word.append(s);

        try {
            var md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(word.toString().getBytes());
            BigInteger no = new BigInteger(1, digest);
            String myHash = no.toString(16);
            while (myHash.length() < 32) {
                myHash = "0" + myHash;
            }

            if (myHash.equals(hash))
            {
                res = word.toString();
                System.out.println("Word with expected hash was found! Word is: "+res);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
