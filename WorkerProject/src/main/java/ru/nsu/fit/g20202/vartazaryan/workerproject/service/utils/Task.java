package ru.nsu.fit.g20202.vartazaryan.workerproject.service.utils;


import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Task
{
    private final String ticketID;
    private final long start;
    private final long checkAmount;
    private final int maxLen;
    private final String hash;
    private List<String> alphabet = new ArrayList<>();

    private List<String> res = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    private long iterations = 0;

    public Task(TaskDTO dto)
    {
        this.ticketID = dto.getTicketID();
        this.start = dto.getStart();
        this.checkAmount = dto.getCheckAmount();
        this.maxLen = dto.getMaxLen();
        this.hash = dto.getHash();

        for(char i = 'a'; i <= 'z'; i++)
            alphabet.add(String.valueOf(i));
        for(char i = '0'; i <= '9'; i++)
            alphabet.add(String.valueOf(i));
    }

    public List<String> run()
    {
        Generator.permutation(alphabet).withRepetitions(maxLen).stream()
                .skip(start)
                .limit(checkAmount)
                .forEach(this::calcHash);

        logger.info(String.format("Execution finished. Total iterations = %d. Sending result...", this.iterations));

        return res;
    }

    private void calcHash(List<String> wordList)
    {
        iterations++;

        double prc = (double) (iterations*100/checkAmount);
        if (iterations % 1_000_000 == 0)
            logger.info(String.format("%s iterations done %d / %d (%.2f%%)", ticketID, iterations, checkAmount, prc));

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
