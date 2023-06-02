package com.xuecheng.media.service.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** 普通bean
 *
 */
@Component
 @Slf4j
public class SampleJob {

 /**
  * 1、简单任务示例（Bean模式）
  */
 @XxlJob("testJob")
 public void testJob() throws Exception {
  log.info("开始执行.....");

 }


 /**
  * 分片广播
  *
  */

 /**
  * 2、分片广播任务
  */
 @XxlJob("shardingJobHandler")
 public void shardingJobHandler() throws Exception {

  // 分片参数
  int shardIndex = XxlJobHelper.getShardIndex(); // 执行器的序号
  int shardTotal = XxlJobHelper.getShardTotal(); // 一共几个执行器

  log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
  log.info("开始执行第"+shardIndex+"批任务");

 }


}