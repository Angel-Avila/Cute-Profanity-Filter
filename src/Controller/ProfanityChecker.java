package Controller;

import Model.Profanity;
import Utils.Utils;

import java.io.File;
import java.util.*;

/**
 * Created by Angel on 4/16/17.
 */
public class ProfanityChecker {

    private ArrayList<String> profanities;
    private ArrayList<String> cuteWords;
    private Set<Profanity> possibleProfanities;
    public StringBuilder unfilteredText;
    public int index;

    private static ProfanityChecker instance;

    private ProfanityChecker() {
        index = 0;
        profanities = new ArrayList<>();
        cuteWords = new ArrayList<>();
        possibleProfanities = new HashSet<>();

        File curDirProf = new File("ProfanityList.txt");
        File curDirCute = new File("CuteWordsList.txt");

        for(String profanity: Utils.readFile(curDirProf).split(",")) {
            profanities.add(profanity);
        }

        for(String cuteWord: Utils.readFile(curDirCute).split(",")) {
            cuteWords.add(cuteWord);
        }
    }

    public static ProfanityChecker getInstance() {
        if(instance == null)
            instance = new ProfanityChecker();
        return instance;
    }

    public void check(StringBuilder text, String letter) {
        boolean didRemove = false;

        for(Iterator<Profanity> iterator = possibleProfanities.iterator(); iterator.hasNext();) {
            Profanity profanity = iterator.next();

            // If we removed a profanity, it means that the other words we have as "possible profanities" weren't actually profanities, so we clean this list
            if(didRemove)
                iterator.remove();
            else {
                profanity.next(letter, index);

                // If the profanity is in an error state it means it wasn't a profanity
                if (profanity.state == State.ERROR)
                    iterator.remove();

                // If it is in final state, it means we found the beginning and end of it, so we replace it with a cute word
                else if (profanity.state == State.FINAL) {
                    replace(text, profanity);
                    didRemove = true;
                    iterator.remove();
                }
            }
        }

        // If the letter we are checking is the same as the first letter of any word in our profanities list, we add it as a possible profanity to be checked in the next call of this method
        for(String profanity: profanities)
            if(profanity.charAt(0) == letter.charAt(0))
                possibleProfanities.add(new Profanity(profanity, index));

        // If we removed a word, we already add 1 to the index, so we shouldn't add 1 at the end of the call
        if(!didRemove)
            index++;
    }

    public void cleanPossibleProfanities(StringBuilder text) {
         for (Iterator<Profanity> iterator = possibleProfanities.iterator(); iterator.hasNext();) {
             Profanity profanity = iterator.next();

             if (profanity.state == State.FINAL0) {
                 replace(text, profanity);
                 iterator.remove();
             }
         }

         unfilteredText = new StringBuilder();
        index = 0;
    }

    private void replace(StringBuilder text, Profanity profanity) {
        Random random = new Random();

        String word = cuteWords.get(random.nextInt(cuteWords.size()));
        text.replace(profanity.startIx, profanity.endIx, word);
        index = word.length() + profanity.startIx + 1;
    }
}