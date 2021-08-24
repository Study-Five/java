package ch7.sunmin;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator {

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        System.out.println("Found " + countWordsByCustomStream(IntStream.range(0, SENTENCE.length())
                                                                        .mapToObj(SENTENCE::charAt)
                                                                        .parallel()) + " words");

        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        System.out.println("Found " + countWordsByCustomStream(stream) + " words");

        System.gc();
    }

    final static String SENTENCE = "Nel     mezzo del   cammin di norstra vita" +
            "mi ritrovavi in una    selva oscura" +
            "ch   la dritta via era  smarrita";

    /**
     * 공백 문자 제외 단어 수 카운팅 (반복형)
     * @param s
     * @return
     */
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for(char c : s.toCharArray()) {
            if(Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if(lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    public static int countWordsByCustomStream(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                                                    WordCounter::accumulate,
                                                    WordCounter::combine);
        return wordCounter.getCounter();
    }
}
