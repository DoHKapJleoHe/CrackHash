package ru.nsu.fit.g20202.vartazaryan.workerproject.service.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.ResponseDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task implements Runnable
{
    private String ticketID;
    private int start;
    private int finish;
    private int maxLen;
    private String hash;
    private List<String> alphabet = new ArrayList<>();

    private List<String> res = new ArrayList<>();
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    private int iterations = 0;

    public Task(TaskDTO dto)
    {
        this.ticketID = dto.getTicketID();
        this.start = dto.getStart();
        this.finish = dto.getFinish();
        this.maxLen = dto.getMaxLen();
        this.hash = dto.getHash();

        for(char i = 'a'; i < 'z'; i++)
            alphabet.add(String.valueOf(i));
        for(char i = '0'; i < '9'; i++)
            alphabet.add(String.valueOf(i));

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run()
    {
        Generator.permutation(alphabet).withRepetitions(maxLen).stream()
                .skip(start)
                .limit(finish)
                .forEach(this::calcHash);

        logger.info(String.format("Execution finished. Total iterations = %d. Sending result...", this.iterations));

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
            logger.info("Result was sent!");

            if (response.statusCode() != 200)
                logger.error(String.format("Something went wrong on manager side. Status code: %d", response.statusCode()));
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
                res.add(word.toString());
                logger.info(String.format("Word with expected hash was found! Word is: %s", res.toString()));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
