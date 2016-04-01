package ww.pjt2;
import java.io.*;
import java.util.*;

/**
 * Created by Yixiao on 19/1/2016.
 */
public class tokenizeFile {
    public String fileName="";
    public String str="";
    public List<String> tokenList=new ArrayList<String>();
    public Map<String,Integer> token_freq=new HashMap<>();
    public List<String> threegram=new ArrayList<>();
    public Map<String,Integer> threegram_freq=new HashMap<>();
    public Set<String> alphanumeric=new HashSet<String>();
    public Map<String, Map<String,Integer>> token_char_freq=new HashMap<String, Map<String, Integer>>();
    public Map<String,ArrayList<String>> token_anagrams=new HashMap<>();
    public int token_count;
    public int max_lines=20000;
    public FileReader filereader=null;
    public BufferedReader br =null;
    public boolean isend;

    public tokenizeFile(String fileName) {
        File file=new File(fileName);
        if(!file.exists()){
            System.out.println("File does not exists");
        }
        else if(file.length()==0){
            System.out.println("File is empty");
        }
        else{
            this.fileName = fileName;
            token_count=0;
            for(int i=0;i<=9;i++) alphanumeric.add(i+"");
            for(char i='a';i<='z';i++) alphanumeric.add(String.valueOf(i));
            for(char i='A';i<='Z';i++) alphanumeric.add(String.valueOf(i));
            isend=false;
        }

    }

    public void tokenize(){
        try{
            filereader=new FileReader(fileName);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        br = new BufferedReader(filereader);

        while(!isend){
            str=readFile();
            str=str.toLowerCase();

            String token="";
            boolean istoken=false;
            //go through string to retrieve tokens(alphanumeric)
            for(int i=0;i<str.length();i++){
                if(alphanumeric.contains(String.valueOf(str.charAt(i)))&&istoken){
                    token=token.concat(String.valueOf(str.charAt(i)));
                }
                else if(alphanumeric.contains(String.valueOf(str.charAt(i)))&&!istoken){
                    token=token.concat(String.valueOf(str.charAt(i)));
                    istoken=true;
                }
                else if(!alphanumeric.contains(String.valueOf(str.charAt(i)))&&istoken){
                    token_count++;
                    istoken=false;
                    tokenList.add(token);

                    //insert into token_freq
                    if(!token_freq.containsKey(token)){
                        token_freq.put(token,1);
                    }
                    else{
                        token_freq.put(token,token_freq.get(token)+1);
                    }
                    token="";
                }

            }
        }

        //writeFile(outputFile);

    }

    public Map<String,Integer> computeWordFrequencies(){
        System.out.println("There are total " + token_count + " tokens, including duplicates");
        return this.token_freq;
    }

    public void printWordFreq(String file) {

        List list = new ArrayList(token_freq.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (int i=list.size()-1;i>=0;i--) {
            Map.Entry entry = (Map.Entry) list.get(i);
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }


        FileWriter writer = null;
        try {

            writer = new FileWriter(file, false);
            Iterator iter = sortedHashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object token = entry.getKey();
                Object freq = entry.getValue();
                writer.write(token.toString()+" : "+freq+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String,Integer> countThreeGramFrequencies(){
        int threegramNum;
        for(threegramNum=0;threegramNum<tokenList.size()-2;threegramNum++){
            String s=tokenList.get(threegramNum)+" "+tokenList.get(threegramNum+1)+" "+tokenList.get(threegramNum+2);
            threegram.add(s);
            if(!threegram_freq.containsKey(s)){
                threegram_freq.put(s,1);
            }
            else if(threegram_freq.containsKey(s)){
                threegram_freq.put(s,threegram_freq.get(s)+1);
            }

        }
        System.out.println("There are total "+threegramNum+" three grams");
        return threegram_freq;
    }

    public void printThreeGramFreq(String file){
        List list = new ArrayList(threegram_freq.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (int i=list.size()-1;i>=0;i--) {
            Map.Entry entry = (Map.Entry) list.get(i);
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }


        FileWriter writer = null;
        try {

            writer = new FileWriter(file, false);
            Iterator iter = sortedHashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object token = entry.getKey();
                Object freq = entry.getValue();
                writer.write(token.toString()+" : "+freq+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFile(){
        int n=0;
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();
            while (line != null) {
                n++;
                sb=sb.append(line);
                sb=sb.append("\n");
                if(n>max_lines){
                    isend=false;
                    break;
                }
                line = br.readLine();
            }
            if(line==null){
                br.close();
                isend=true;
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void printTokenList(String fileName){
        FileWriter writer = null;
        try {

            writer = new FileWriter(fileName, false);
            for(String s:tokenList){
                writer.write(s+" ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void print(){
        for(String s:tokenList){
            System.out.print(s + " ");
        }
    }

    public Map<String,Map<String, Integer>> compute_char_freq(){
        List<String> list=new ArrayList<String>();
        Iterator iter = token_freq.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String token = entry.getKey().toString();
            list.add(token);
        }

        for(String str:list){
            Map<String,Integer> map=new HashMap<>();
            for(int i=0;i<str.length();i++){
                if(!map.containsKey(String.valueOf(str.charAt(i)))) map.put(String.valueOf(str.charAt(i)),1);
                else map.put(String.valueOf(str.charAt(i)),map.get(String.valueOf(str.charAt(i)))+1);
            }

            token_char_freq.put(str, map);
        }
        return token_char_freq;
    }

    //compute token anagrams and store them into token_anagrams
//    public void detectAnagrams(precompute_word pre){
//        Map<String,Integer> char_to_index=pre.char_to_index;
//        for(String str:tokenList){
//            String word_char="000000000000000000000000000";
//
//
//            int index;
//            for(int i=0;i<str.length();i++){
//                String s=String.valueOf(str.charAt(i));
//                if(char_to_index.containsKey(s))
//                    index=char_to_index.get(s);
//                else continue;
//                if(index==0){
//                    String change=String.valueOf(word_char.charAt(index));
//                    String keep=word_char.substring(1, 26);
//                    String keep_lastone=String.valueOf(word_char.charAt(26));
//                    int temp=Integer.parseInt(change);
//                    temp++;
//                    change=String.valueOf(temp);
//                    word_char=null;
//                    word_char=change+keep+keep_lastone;
//                }
//                else if(index==26){
//                    String keep=word_char.substring(0, 26);
//                    String change=String.valueOf(word_char.charAt(26));
//                    int temp=Integer.parseInt(change);
//                    temp++;
//                    change=String.valueOf(temp);
//                    word_char=null;
//                    word_char=keep+change;
//                }
//                else{
//                    String change=String.valueOf(word_char.charAt(index));
//                    String keep1=word_char.substring(0, index);
//                    String keep2=word_char.substring(index + 1, 26);
//                    String keep_lastone=String.valueOf(word_char.charAt(26));
//                    int temp=Integer.parseInt(change);
//                    temp++;
//                    change=String.valueOf(temp);
//                    word_char=null;
//                    word_char=keep1+change+keep2+keep_lastone;
//                }
//
//            }
//            if(pre.anagram_map.containsKey(word_char)){
//                ArrayList<String> anagrams=new ArrayList<>();
//                anagrams=pre.anagram_map.get(word_char);
//
//                ArrayList<String> anagrams_unique=new ArrayList<>();
//                int s=anagrams.size();
//                //delete the anagrams that are same with token
//                for(int i=0;i<s;i++){
//                    int n=0;
//                    for(int j=0;j<str.length();j++){
//                        if(str.charAt(j)==anagrams.get(i).charAt(j)){
//                            n++;
//                        }
//                    }
//                    if(n!=str.length()){
//                        //System.out.println(str+" "+anagrams.get(i));
//                        anagrams_unique.add(anagrams.get(i));
//                    }
//
//                }
//
//
//                if(!token_anagrams.containsKey(str)&&!anagrams_unique.isEmpty()){
//                    token_anagrams.put(str,anagrams_unique);
//                }
//            }
//
//        }
//    }

    public void printAnagrams(String file){
        List list = new ArrayList(token_anagrams.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getKey())
                        .compareTo(((Map.Entry) (o1)).getKey());
            }
        });
        HashMap sorted = new LinkedHashMap();
        for (int i=list.size()-1;i>=0;i--) {
            Map.Entry entry = (Map.Entry) list.get(i);
            sorted.put(entry.getKey(), entry.getValue());
        }

        FileWriter writer = null;
        try {

            writer = new FileWriter(file, false);
            Iterator iter = sorted.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object token = entry.getKey();
                Object anagrams = entry.getValue();
                writer.write(token.toString()+":"+anagrams+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
