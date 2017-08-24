package com.mahout;

/**
 * Created by Administrator on 2017/8/2.
 */
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import java.io.*;
import java.util.*;

public class RecommenderIntro {
    public static void main(String[] args) throws Exception {

        DataModel model = new FileDataModel(new File("D:\\test\\intro.csv"));//加载数据文件
        UserSimilarity similarity = new PearsonCorrelationSimilarity (model);  //建立推荐模型
        UserNeighborhood neighborhood =  new NearestNUserNeighborhood (2, similarity, model);
        Recommender recommender = new GenericUserBasedRecommender ( model, neighborhood, similarity);
        List<RecommendedItem > recommendations = recommender.recommend(1, 2); //给用户1推荐2个项目
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}