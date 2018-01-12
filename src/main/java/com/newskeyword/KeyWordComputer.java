package com.newskeyword;

/**
 * Created by Administrator on 2017/12/27.
 */
import org.ansj.app.newWord.LearnTool;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

public class KeyWordComputer {
    private int nKeyword = 10;
    //default constructor keyword number=10
    public KeyWordComputer() {
        nKeyword = 10;
    }
    // constructor set keyword number
    public KeyWordComputer(int nKeyword) {
        this.nKeyword = nKeyword;

    }
    //get keywords object list
    private List<Keyword> computeArticleTfidf(String content, int titleLength) throws Exception {

        GetStopWordList getStopWordList = new GetStopWordList();
        Map<String, List> map = getStopWordList.getStopWordList();
        List<String> list_c = map.get("MacroDef.STOP_CHINESE");
        List<String> list_e = map.get("MacroDef.STOP_ENGLISH");

        Map<String, Keyword> tm = new HashMap<String, Keyword>();
        LearnTool learn = new LearnTool();
        List<Term> parse = NlpAnalysis.parse(content, learn);
        parse = NlpAnalysis.parse(content, learn);
        for (Term term : parse) {
            boolean flag = true;
            String str = term.getName().trim();
            for (String str_c : list_c) {
                if (str_c.equals(str.toString()))
                    flag = false;
            }
            for (String str_e : list_e) {
                if (str_e.equals(str.toString()))
                    flag = false;
            }

            if(flag) {
                int weight = getWeight(term, content.length(), titleLength);
                if (weight == 0)
                    continue;
                Keyword keyword = tm.get(term.getName());
                if (keyword == null) {
                    keyword = new Keyword(term.getName(), term.getNatrue().allFrequency, weight);
                    tm.put(term.getName(), keyword);
                } else {
                    keyword.updateWeight(1);
                }
            }
        }
        TreeSet<Keyword> treeSet = new TreeSet<Keyword>(tm.values());
        ArrayList<Keyword> arrayList = new ArrayList<Keyword>(treeSet);
        if (treeSet.size() < nKeyword) {
            return arrayList;
        } else {
            return arrayList.subList(0, nKeyword);
        }
    }
    //get keywords,need title and content
    public Collection<Keyword> computeArticleTfidf(String title, String content) throws Exception {
        return computeArticleTfidf(title + "\t" + content, title.length());
    }
    //get keywords, just need content
    public Collection<Keyword> computeArticleTfidf(String content) throws Exception {
        return computeArticleTfidf(content, 0);
    }
    //get keywords weight
    private int getWeight(Term term, int length, int titleLength) {
        if (term.getName().matches("(?s)\\d.*")) {
            return 0;
        }
        if (term.getName().trim().length() < 2) {
            return 0;
        }
        String pos = term.getNatrue().natureStr;
        if (!pos.startsWith("n") || "num".equals(pos)) {
            return 0;
        }
        int weight = 0;
        if (titleLength > term.getOffe()) {
            return 20;
        }
        // position
        double position = (term.getOffe() + 0.0) / length;
        if (position < 0.05)
            return 10;
        weight += (5 - 5 * position);
        return weight;
    }
}
