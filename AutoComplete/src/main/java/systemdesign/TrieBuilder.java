package systemdesign;

import systemdesign.ds.Trie;
import systemdesign.ds.Word;

import java.io.*;
import java.util.ArrayList;

public class TrieBuilder {

    public static String WORDS_FILE = "C:\\mystuff\\tekshila_repo\\AutoComplete\\target\\classes\\words.txt";
    public static String WORDS_FREQ_FILE = "C:\\mystuff\\tekshila_repo\\AutoComplete\\target\\classes\\words_freq.txt";

    public Trie buildTrie() {

        ArrayList<String> wordsList = loadWords();

//        wordsList.forEach(System.out::println);
        Trie t = new Trie();

        wordsList.forEach(w -> t.insert(w));

        t.printTrie();

        return t;
    }

    public Trie buildTrieWithFreq() {

        ArrayList<Word> wordsList = loadWordsWithFreq();

//        wordsList.forEach(System.out::println);
        Trie t = new Trie();

        wordsList.forEach(w -> t.insert(w.getWord(),w.getFrequency()));

//        t.printTrie();

        return t;
    }

    public ArrayList<String> loadWords() {
        ArrayList<String> wordsList = new ArrayList<>();
        File f = new File(WORDS_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String temp = "";
            while( (temp = br.readLine()) != null) {
                wordsList.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsList;
    }

    public ArrayList<Word> loadWordsWithFreq() {
        ArrayList<Word> wordsList = new ArrayList<>();
        File f = new File(WORDS_FREQ_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String temp = "";
            while( (temp = br.readLine()) != null) {
//                System.out.println(temp);
                String[] tokens = temp.split(",");
                wordsList.add(new Word(tokens[0], Integer.parseInt(tokens[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsList;
    }


    public static void main(String[] args) {
        TrieBuilder tbld = new TrieBuilder();
        Trie trieObj = tbld.buildTrieWithFreq();

            trieObj.populateAutoComplete();
            ArrayList<Word> results = trieObj.search("am");

            results.forEach(w -> {
                System.out.println("{ " + w.getWord() + " , " + w.getFrequency() + "}");
            });
    }
}
