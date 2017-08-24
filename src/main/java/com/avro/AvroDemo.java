package com.avro;

/**
 * Created by Administrator on 2017/5/3.
 */
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import java.io.File;
import java.io.IOException;
public class AvroDemo {
    String fileName="D:\\tmp\\student.db";
    String prefix="{\"type\":\"record\",\"name\":\"student\",\"fields\":[";
    String suffix="]}";
    String fieldSID="{\"name\":\"SID\",\"type\":\"int\"}";
    String fieldName="{\"name\":\"Name\",\"type\":\"string\"}";
    String fieldDept="{\"name\":\"Dept\",\"type\":\"string\"}";
    String fieldPhone="{\"name\":\"Phone\",\"type\":\"string\"}";
    String fieldAge="{\"name\":\"Age\",\"type\":\"int\"}";
    Schema studentSchema=Schema.parse(prefix+fieldSID+","+fieldName+","+fieldDept+","+fieldPhone+","+fieldAge+suffix);
    Schema extractSchema=Schema.parse(prefix+fieldName+","+fieldPhone+suffix);
    int SID=0;

    public static void main(String[] args) throws IOException{
        AvroDemo avd=new AvroDemo();
        avd.init();
        avd.print();
        avd.printExtraction();
    }

    //初始化学生记录
    public void init() throws IOException{
        DataFileWriter<Record> writer=new DataFileWriter<Record>(
                new GenericDatumWriter<Record>(studentSchema)).create(studentSchema,new File(fileName));
        try{
            writer.append(createStudent("zhanghua","Law","15201161111",25));
            writer.append(createStudent("Lili","Economy","15201162222",24));
            writer.append(createStudent("Wangyu","Infomation","15201163333",23));
            writer.append(createStudent("Zhaoxin","Art","15201164444",25));
            writer.append(createStudent("Sunqin","Physics","15201165555",23));
            writer.append(createStudent("Zhouping","Math","15201166666",23));
        }finally{
            writer.close();
        }
    }

    //添加学生记录
    private Record createStudent(String name,String dept,String phone,int age){
        Record student=new Record(studentSchema);
        student.put("SID", (++SID));
        student.put("Name", name);
        student.put("Dept", dept);
        student.put("Phone",phone);
        student.put("Age",age);
        System.out.println("successfully added"+name);
        return student;
    }

    //输出学生信息
    public void print() throws IOException{
        GenericDatumReader<Record> dr=new GenericDatumReader<Record>();
        dr.setExpected(studentSchema);
        DataFileReader<Record> reader=new DataFileReader<Record>(new File(fileName),dr);
        System.out.println("\n print all the records from database");
        try{
            while(reader.hasNext()){
                Record student=reader.next();
                System.out.print(student.get("SID").toString()+" "+student.get("Name")+" "+student.get("Dept")+" "+student.get("Phone")+" "+student.get("Age").toString()+"\r\n");
            }
        }finally{
            reader.close();
        }
    }

    //输出学生姓名和电话
    public void printExtraction() throws IOException{
        GenericDatumReader<Record> dr=new GenericDatumReader<Record>();
        dr.setExpected(extractSchema);
        DataFileReader<Record> reader=new DataFileReader<Record>(new File(fileName),dr);
        System.out.println("\n Extract Name&Phone of the records from database");
        try{
            while(reader.hasNext()){
                Record student=reader.next();
                System.out.print(student.get("Name")+" "+student.get("Phone")+"\r\n");
            }
        }finally{
            reader.close();
        }
    }

}
