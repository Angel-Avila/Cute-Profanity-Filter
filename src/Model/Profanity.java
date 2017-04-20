package Model;

import Controller.State;
import Utils.Utils;

/**
 * Created by Angel on 4/16/17.
 */
public class Profanity {

    public String word;
    public int startIx;
    public int endIx;
    public int currentIx;
    public State state;

    public Profanity(String word, int startIx) {
        this.word = word;
        this.startIx = startIx;
        endIx = -1;
        currentIx = 0;
        state = State.CHECKING;
    }

    public void next(String letter, int textIx) {
        // These numbers can sometimes be used in text to replace characters; e.g., "H3ll0" can be read as "Hello"
        if(letter.matches("[013457]"))
            letter = Utils.toLetter(letter);

        if(word.equals("bitch"))
            System.out.println();

        // We passed the same letter, this condition is so the profanity filter recognizes a word like "heeeeello" as "hello"
        if (letter.charAt(0) == word.charAt(currentIx)) {
            if(word.length() != currentIx + 1)
                if(word.charAt(currentIx + 1) == word.charAt(currentIx))
                    currentIx++;
            return;
        }

        // This means the word and repetitions of the last letter are over and we are encountering a new word
        // e.g., we were checking  a text with "...hellooooo friend..." and now we're being passed the "f"
        if(word.length() == currentIx + 1) {
            if(state != State.FINAL0)
                endIx = textIx;

            state = State.FINAL;

            return;
        }

        // If not, we increase the index for this profanity
        currentIx++;

        // If the letter we are checking now is not the same as the one we are checking in our profanity, it isn't a profanity
        if(letter.charAt(0) != word.charAt(currentIx))
            state = State.ERROR;

        // If it came here, it's because the letter we are checking IS the same as the one we are checking in our profanity
        // and if we are at the very end of the profanity, it is finished, but we are looking for repetitions of the last letter
        else if(word.length() == currentIx + 1) {
            state = State.FINAL0;
            endIx = textIx + 1;
        }
    }
}