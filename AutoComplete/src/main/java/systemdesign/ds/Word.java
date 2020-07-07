package systemdesign.ds;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Word implements Comparable<Word> {
    private String word;
    private int frequency;

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Word obj) {
        return this.frequency - obj.frequency;
    }
}
