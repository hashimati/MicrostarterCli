package io.hashimati.security.utils;

import jakarta.inject.Singleton;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Random;


@Singleton
public class CodeRandomizer {
    // A= 65- Z=90  , a= 97 z =122, 0 =48, 9 = 57
    ArrayList<Integer> rndSet = new ArrayList<>();

    @PostConstruct
    private void init() {
        for (int i = 65; i <= 90; i++) {
            rndSet.add(i);
        }
        for (int i = 48; i <= 57; i++) {
            rndSet.add(i);
        }
    }

    public String getRandomString(int length) {
        Random r = new Random();
        StringBuilder random = new StringBuilder("");
        for (int i = 1; i <= length; i++) {
            random.append((char)(r.nextInt(10) + 48));
        }
        return random.toString();
    }

       public String randomOTP(int length){
        if(length < 1){
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        Random r = new Random();
        StringBuilder random = new StringBuilder("");
        for (int i = 1; i <= length; i++) {
            random.append((char)(r.nextInt(10) + 48));
        }
        return random.toString();
    }
}


