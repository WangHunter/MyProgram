package com.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */
public class LogWriter extends BaseRichBolt {

    private HashMap<String, Long> counts=null;

    private FileWriter writer=null;
    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        // TODO 自动生成的方法存根
        this.counts=new HashMap<String, Long>();
        try {
            writer=new FileWriter("/"+this);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple input) {
        // TODO 自动生成的方法存根
        String srcid=input.getStringByField("srcid");
        Long pay=input.getLongByField("pay");
        counts.put(srcid, pay);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO 自动生成的方法存根

    }
    @Override
    public void cleanup() {
        // TODO 自动生成的方法存根
        Iterator<Map.Entry<String,Long>> it=counts.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry<String,Long> entry=it.next();
            try {
                writer.write(entry.getKey()+" : "+entry.getValue());
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
