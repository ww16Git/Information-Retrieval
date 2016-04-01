package edu.ww.entity;

import java.util.ArrayList;

public class Postings {
    public int docid;
    public int term_freq;
    public double tfidf;
    public ArrayList<Integer> positions=new ArrayList<>();

    public Postings(int d, double tf, ArrayList<Integer> posi){
        this.docid = d;
        //this.term_freq = t;
        this.tfidf = tf;
        this.positions=posi;
    }

    public Postings(){

    }
}
