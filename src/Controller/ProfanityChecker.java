package Controller;

import Model.Profanity;

import java.util.ArrayList;

/**
 * Created by Angel on 4/16/17.
 */
public class ProfanityChecker {

    private ArrayList<String> profanities;
    private ArrayList<Profanity> possibleProfanities;
    public String unfilteredText;
    public int index;

    private static final ProfanityChecker instance = new ProfanityChecker();

    private ProfanityChecker() {
        index = 0;
    }

    public static ProfanityChecker getInstance() {
        return instance;
    }

    public void check(StringBuilder text, String letter) {

    }

}
