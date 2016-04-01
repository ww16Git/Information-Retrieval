package ww.pjt;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yixiao on 21/2/2016.
 */
public class Postings {
    public int docid;
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
