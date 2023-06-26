package com.xuecheng.media.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.po.MediaFiles;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
  * @author Mr.M
  * @date 2022/9/10 8:57
 */
 public PageResult<MediaFiles> queryMediaFiels(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

 /**
  * 上传文件
  * @param companyId 机构id
  * @param uploadFileParamsDto 上传文件信息
  * @param localFilePath 文件磁盘路径
  * @return 文件信息
  */
// public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath,String objectName);

 public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName);


 public RestResponse<Boolean> checkFile(String fileMd5) throws IOException;

 public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

 //
 public RestResponse uploadChunk(String fileMd5,int chunkIndex,String absolutePath);
 //uploadChunk(String fileMd5,int chunk,byte[] bytes);


 public RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);

 MediaFiles getFileById(String mediaId);


 // 下载
 public File downloadFileFromMinIO(String bucket, String objectName);


 // 上传
 public boolean addMediaFilesToMinIO(String localFilePath,String mimeType,String bucket, String objectName);




}
