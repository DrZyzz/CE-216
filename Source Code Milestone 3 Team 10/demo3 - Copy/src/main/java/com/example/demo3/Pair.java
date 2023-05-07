package com.example.demo3;

import java.util.ArrayList;

public class Pair {
    public Languages source;
    public Languages target;

    public Pair(Languages source, Languages target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return source.toString().toLowerCase() + "-" + target.toString().toLowerCase();
    }

    public String getIndexFileName() {
        return "Language Files/" + toString() + ".index";
    }

    public String getDictFileName() {
        return "Language Files/" + toString() + ".dict";
    }

    public static Languages getFromString(String choice) {
        switch (choice) {
            case "ENG":
                return Languages.ENG;
            case "TUR":
                return Languages.TUR;
            case "DEU":
                return Languages.DEU;
            case "FRA":
                return Languages.FRA;
            case "SWE":
                return Languages.SWE;
            case "ITA":
                return Languages.ITA;
            case "ELL":
                return Languages.ELL;
        }
        return null;
    }

        public static Translator getTranslatorFromPair(Pair pair, ArrayList<Translator> translatorArrayList) {

        for (Translator t : translatorArrayList) {
            if (t.pair != null && t.pair.target == pair.target && t.pair.source == pair.source) {
                return t;
            }
        }
        return null;
    }
}
