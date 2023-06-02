package com.xuecheng.media;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MinIOTest {



    // 创建MinioClient对象
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    /**
     * 上传测试方法
     */
    @Test
    public void uploadTest() {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("testbucket")
                            .object("test/111.png")    // 同一个桶内对象名不能重复
                            .filename("E:\\test\\picture\\111.png")
                            .build()
            );
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }

    @Test
    public void delete(){
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket("testbucket").object("test/111.png").build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    //查询文件

    @Test
    public void getFileTest() {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("testbucket")
                    .object("test/111.png")
                    .build());
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\test\\picture\\tmp1.png");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer,0,len);
            }
            inputStream.close();
            fileOutputStream.close();
            System.out.println("下载成功");
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
    @Test
    public void getFileTest1() {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("testbucket")
                    .object("test/111.png")
                    .build());
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\test\\picture\\temp1.png");
            IOUtils.copy(inputStream,fileOutputStream);
            //校验文件的完整性对文件的内容进行md5
            FileInputStream fileInputStream = new FileInputStream("E:\\test\\picture\\temp1.png");
            String minIO_md5 = DigestUtils.md5Hex(inputStream);
            String down_md5 = DigestUtils.md5Hex(fileInputStream);
            if(minIO_md5.equals(down_md5)){
                System.out.println("minio与下载文件校验一致");
            }else{
                System.out.println("minio与下载文件校验不一致？"); //为什么？
            }
            FileInputStream originInputStream = new FileInputStream("E:\\test\\picture\\111.png");
            String origin_local_md5 = DigestUtils.md5Hex(originInputStream);
            if(origin_local_md5.equals(down_md5)){
                System.out.println("本地与下载文件校验一致");
            }else{
               System.out.println("本地与下载文件校验不一致");
            }



        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }


    // 将分块文件上传到 minio
    @Test
    public void uploadChunk() throws Exception {

        // 分块文件夹
        String chunkFilePath = "E:\\test\\vvefdio\\Chunk\\";

        //
        File[] files = new File(chunkFilePath).listFiles();
        for (int i = 0; i < files.length; i++) {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("testbucket")
                            .object("test/chunkFiles/"+i)    // 同一个桶内对象名不能重复
                            .filename(chunkFilePath+i)
                            .build()
            );
        }

    }

    // 合并文件
    @Test
    public void testMerge() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        // 获取分块文件目录，获取里面的所有应该合并的文件
        List<ComposeSource> sources = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ComposeSource composeSource = ComposeSource.builder()
                    .bucket("testbucket")
                    .object("test/chunkFiles/" + i)
                    .build();
            sources.add(composeSource);
        }



        minioClient.composeObject(
                ComposeObjectArgs.builder()
                        .bucket("testbucket")
                        .sources(sources)
                        .object("test/mergeFile.mp4") // 合并后的name
                        .build()
        );

        // 发现报错IllegalArgumentException: source  size 1048576 must be greater than 5242880
        // 也就是 minio 默认分块文件大小应该是5MB


    }


    // 清理分块文件
}
