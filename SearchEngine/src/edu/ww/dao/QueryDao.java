package edu.ww.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface QueryDao {
	public ArrayList<Integer> ranking(HashMap<Integer, ArrayList<Double>> accumulator);
	public HashMap<Integer, ArrayList<Double>> query(ArrayList<String> terms);
}
