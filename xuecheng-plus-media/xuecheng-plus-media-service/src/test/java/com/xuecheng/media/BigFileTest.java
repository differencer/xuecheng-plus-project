package com.xuecheng.media;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.AssertTrue;
import java.io.*;
import java.util.*;

/** 测试大文件分块上传
 *
 */
public class BigFileTest {
    /**
     * 将文文件分块
     */
    @Test
    public void testChunk() throws IOException {
        // 1、找到源文件
        File sourceFile = new File("E:\\test\\vvefdio\\sss.mp4");

        // 将文件分块，分块文件的存储路径
        String chunkFileFolder = "E:\\test\\vvefdio\\Chunk\\";

        // 分块文件的大小
        int chunkSize = 1024 *1024 *5 ;// 1MB
        // 分块文件的个数
        int chunkNum = (int) Math.ceil(sourceFile.length() * 1.0/ chunkSize);
        // 填充分块文件(随机流)
        RandomAccessFile rand_r = new RandomAccessFile(sourceFile, "r");// 读取源文件


        // 缓冲区 用于存储读取的数据
        byte[] buffer = new byte[1024];
        for(int i=0;i<chunkNum;i++){
            File chunkFile = new File(chunkFileFolder+i); // 分块文件的 路径、名称
            // 分块文件的写入流
            RandomAccessFile rand_rw = new RandomAccessFile(chunkFile,"rw");
            int len = -1;
            while((len=rand_r.read(buffer))!=-1){
                rand_rw.write(buffer,0,len); // 把 buffer 里面的内容往 目标晚间里面写
                if(chunkFile.length()>=chunkSize){
                    break;
                }
            }
            rand_rw.close();
        }
        rand_r.close();

    }

    @Test
    public void testMerge() throws IOException {
        // 将文件分块，分块文件的存储路径
        String chunkFileFolder = "E:\\test\\vvefdio\\Chunk\\";
        // 分块文件的大小
        int chunkSize = 1024 *1024 *5 ;// 1MB
        // 合并文件
        File mergeFile = new File("E:\\test\\vvefdio\\sss_merge.mp4");

        // 取出所有的分块文件
        File[] files_array = new File(chunkFileFolder).listFiles();
        List<File> files = Arrays.asList(files_array);
        // 按序读取
        Collections.sort(files,new Comparator<File>(){
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
            }
        });

        // 遍历分块，向合并文件写
        RandomAccessFile rand_rw = new RandomAccessFile(mergeFile,"rw");
        byte[] buffer = new byte[1024];
        for (File file : files) {
            // 读取 分块
            RandomAccessFile rand_r = new RandomAccessFile(file,"r");
            //
            int len=-1;
            while((len=rand_r.read(buffer))!=-1){
                rand_rw.write(buffer,0,len);
            }
            rand_r.close();
        }
        rand_rw.close();


        // 校验 md5
        // 源文件
        File soureFile= new File("E:\\test\\vvefdio\\sss.mp4");
        InputStream sourceIn = new FileInputStream(soureFile);
        String s1=DigestUtils.md5DigestAsHex(sourceIn);
        // 合并文件
        //File mergeFile= new File("E:\\test\\vvefdio\\sss_merge.mp4");
        InputStream mergeIn = new FileInputStream(soureFile);
        String s2=DigestUtils.md5DigestAsHex(mergeIn);

        Assert.isTrue(s1.equals(s2));
    }




}
