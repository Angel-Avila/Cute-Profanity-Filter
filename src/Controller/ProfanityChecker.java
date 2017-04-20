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
    public String unfilteredText;
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

            if(didRemove)
                iterator.remove();
            else {

                profanity.next(letter, index);

                if (profanity.state == State.ERROR)
                    iterator.remove();

                else if (profanity.state == State.FINAL) {
                    replace(text, profanity);
                    didRemove = true;
                    iterator.remove();
                }
            }
        }

        for(String profanity: profanities)
            if(profanity.charAt(0) == letter.charAt(0))
                possibleProfanities.add(new Profanity(profanity, index));

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

         unfilteredText = "";
    }

    private void replace(StringBuilder text, Profanity profanity) {
        Random random = new Random();

        String word = cuteWords.get(random.nextInt(cuteWords.size()));
        text.replace(profanity.startIx, profanity.endIx, word);
        index = word.length() + profanity.startIx + 1;
    }

}
