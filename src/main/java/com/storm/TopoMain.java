package com.storm;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by Administrator on 2017/11/29.
 */

public class TopoMain {
        public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

            TopologyBuilder builder=new TopologyBuilder();
            builder.setSpout("log-vspout", new VSpout(),1);
            builder.setSpout("log-bspout", new BSpout(),1);

            //定义两个流名称，分别为visit和business
            builder.setBolt("log-merge", new LogMergeBolt(),2).fieldsGrouping("log-vspout",
                    "visit",new Fields("user")).fieldsGrouping("log-bspout","business",new
                    Fields("user"));

            builder.setBolt("log-stat", new LogStatBolt(),2).fieldsGrouping("log-merge",new
                    Fields("srcid"));

            builder.setBolt("log-writer", new LogWriter()).globalGrouping("log-stat");

            Config conf=new Config();

            //实时计算不需要可靠消息，故关闭acker节省通信资源
            conf.setNumAckers(0);
            //设置独立java进程数，一般设置为同spout和bolt的总tasks数量相等或更多，使得每个task
            //都运行在独立的Java进程中，以避免多个task集中在一个jvm里运行产
            //生GC(垃圾回收机制)瓶颈
            conf.setNumWorkers(8);
            StormSubmitter.submitTopology("ElectronicCom_Top", conf, builder.createTopology());

//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology("ElectronicCom_Top", conf, builder.createTopology());
        }


}