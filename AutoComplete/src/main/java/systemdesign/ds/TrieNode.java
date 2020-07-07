package systemdesign.ds;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter @Setter
public class TrieNode {
    private int ALPHABET_SIZE = 26;// last is for space
    private char c; // character at the node
    private TrieNode[] children; // 26 nodes at each level
    private ArrayList<Word> topWords;
    private boolean isWordComplete;
    private String fullWord;
    private int fullWordFreq;

    public TrieNode() {
        children = new TrieNode[ALPHABET_SIZE];
        topWords = new ArrayList<>();
    }

    public TrieNode(char c) {
        this();
        this.c = c;
    }

    public void addTopWord(String w, int f) {
        this.topWords.add(new Word(w,f));
    }

    public void populateTopWords() {
//        if(isWordComplete) {
//            this.topWords.add(new Word(fullWord, Integer.MAX_VALUE)); // highest priority to typed word
//        }
        for(TrieNode childNode : children) {
            if(null == childNode) continue;
            if(null == childNode.getTopWords() || childNode.getTopWords().size() <= 0) childNode.populateTopWords();
            this.topWords.addAll(childNode.getTopWords());
            this.topWords.sort((Word w1, Word w2) -> w2.getFrequency() - w1.getFrequency());
        }
//        this.topWords = this.topWords.stream().filter(s -> s./);
    }

    public void printTrieNode() {
        System.out.println("---" + c);
//        System.out.println("---" + this.hashCode());
        System.out.print("--");

        Arrays.stream(children).filter(s -> s!= null).forEach(x -> {
            System.out.print("--");
            x.printTrieNode();
            if(x.isWordComplete()) System.out.println(" << full word : " + x.getFullWord());
        });

    }

}
