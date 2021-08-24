package ch7.sunmin;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); // 현재 문자를 소비한다. 소비한 후 index 를 높여준다.
        return currentChar < string.length(); // 소비할 문자가 남아있으면 true
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if(currentSize < 10) {
            return null; // 파싱할 문자열을 순차처리할 수 있을만큼 충분히 작아졌다면 null
        }
        for(int splitPos = currentSize / 2 + currentChar;
                splitPos < string.length(); splitPos++) {
            if(Character.isWhitespace(string.charAt(splitPos))) { // 다음 공백이 나올때까지 분할위치를 뒤로 이동시킨다.
                Spliterator<Character> spliterator =
                        new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator; // 공백을 찾았고 여기까지만 분할한다.
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
