package Tools;

import java.util.Comparator;

import Main.*;

//a tool
public class PokerSort implements Comparator<Pokers> {
    @Override
    public int compare(Pokers a, Pokers b) {
        return a.getNum() - b.getNum();
    }
}

