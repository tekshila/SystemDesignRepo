package systemdesign.ds;

import java.util.ArrayList;
import java.util.Arrays;

public class Trie {

    private TrieNode root;
    private int ARR_DIFF = 97; // for small case
    private static int SPACE_VAL = 32;
    private static int LAST_INDEX = 26;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode runningNode = root;
        for(char c : word.toLowerCase().toCharArray()) {
            int index = (int)c - ARR_DIFF;
            if(c == SPACE_VAL) {
                index= LAST_INDEX;
            }
                runningNode.getChildren()[index] = new TrieNode(c);
                runningNode = runningNode.getChildren()[index];
        }
        runningNode.setWordComplete(true);
        runningNode.setFullWord(word);
    }

    public void insert(String word, int freq) {
        TrieNode runningNode = root;
        for(char c : word.toLowerCase().toCharArray()) {
            int index = (int)c - ARR_DIFF;
//            if(c == SPACE_VAL) {
//                index= LAST_INDEX;
//            }
            if(null == runningNode.getChildren()[index]) {
                runningNode.getChildren()[index] = new TrieNode(c);
            }
            runningNode = runningNode.getChildren()[index];

        }
        runningNode.setWordComplete(true);
        runningNode.setFullWord(word);
        runningNode.setFullWordFreq(freq);
        runningNode.addTopWord(word,freq);
    }



    public ArrayList<Word> search(String word) {
        TrieNode runningNode = root;
        for(char c : word.toLowerCase().toCharArray()) {
            int index = (int)c - ARR_DIFF;
//            if(c == SPACE_VAL) {
//                index= LAST_INDEX;
//            }
            if(index < 0) return null;
            runningNode = runningNode.getChildren()[index];
            if(runningNode == null) return null;
        }
        return runningNode.getTopWords();
    }

    public void populateAutoComplete() {
       TrieNode currentNode = root;

       for(TrieNode t : currentNode.getChildren()) {
           if(null == t) continue;
           if(null == t.getTopWords() || t.getTopWords().size() <= 0) t.populateTopWords();
       }
    }

    public void printTrie() {
        System.out.print("--");
        Arrays.stream(root.getChildren()).filter(s -> s != null).forEach(x -> {
//            System.out.println(" object id --> " + x.hashCode());
            x.printTrieNode();
            if(x.isWordComplete()) System.out.print("\t--- word : " + x.getFullWord() + "\t freq : " + x.getFullWordFreq());
        });
    }

    public static void main(String[] args) {
        Trie t = new Trie();
           t.insert("harpreet");
           t.printTrie();
    }

}
