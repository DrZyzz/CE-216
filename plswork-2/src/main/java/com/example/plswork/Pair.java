package com.example.plswork;

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

    public String getIndexFileName(){
       return toString() + ".index";
    }

    public String getDictFileName(){
        return toString() + ".dict";
    }

}
