package org.example;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int maxKey = 0;
        int maxValue = 0;
        int threads = 1000;
        //System.out.println("Поехали");

        Runnable r = () -> {
            String s = generateRoute("RLRFR", 100);
            repRateToMap(s);
        };

        for (int i = 0; i < threads; i++) {
            String threadName = "MyThread" + i;
            Thread myThread = new Thread(r, threadName);
            myThread.start();

        }
        maxKey = FindMaxKeyInMap(sizeToFreq);
        maxValue = sizeToFreq.get(maxKey);


        System.out.println("Самое частое количество повторений " +
                maxKey + " (встретилось " +
                maxValue +
                " раз)");// Самое частое количество повторений 61 (встретилось 9 раз)
        System.out.println("Другие размеры:");
        for (Map.Entry entry : sizeToFreq.entrySet()) {
            int key = (int) entry.getKey();
            int value = (int) entry.getValue();
            if (key == maxKey) {
                continue;
            }

            System.out.println("- " + key + " (" + value + " раз)");//- 60 (5 раз)
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void repRateToMap(String string) {
        int rep = 0;
        int count = 0;
        synchronized (sizeToFreq) {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == 'R') {
                    rep++;
                }
            }
            if (sizeToFreq.containsKey(rep)) {
                count = sizeToFreq.get(rep) + 1;
            } else {
                count++;
            }
            sizeToFreq.put(rep, count);
        }
    }

    public static int FindMaxKeyInMap(Map<Integer, Integer> map) {
        Optional<Map.Entry<Integer, Integer>> maxEntry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        return maxEntry.get()
                .getKey();
    }


}