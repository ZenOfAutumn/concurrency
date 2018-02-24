package com.autumn.zen.thread.executor;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Completion Service Example
 **/
public class CompletionServiceEx {

  public static void main(String[] args) {

    ExecutorService executor = Executors
      .newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());
    CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
    int size = 100;
    for (FileInfo fileInfo : mockFileInfo(size)) {
      completionService.submit(new DownloadTask(fileInfo));
    }

    try {
      for (int i = 0; i < size; i++) {
        Future<String> future = completionService.take();
        System.out.println(future.get() + " download succeed");
      }
    } catch (InterruptedException e) {

    } catch (ExecutionException e) {

    }finally {
      executor.shutdown();
    }
  }

  public static List<FileInfo> mockFileInfo(int size) {
    List<FileInfo> fileInfos = Lists.newArrayList();
    for (int i = 0; i < size; i++) {
      fileInfos.add(new FileInfo("http://www.google.com/" + i + ".html", String.valueOf(i)));
    }
    return fileInfos;
  }

  public static class DownloadTask implements Callable<String> {

    private FileInfo fileInfo;

    public DownloadTask(FileInfo fileInfo) {
      this.fileInfo = fileInfo;
    }

    @Override
    public String call() throws Exception {
      // simulate download process
      Thread.sleep(1000);
      return fileInfo.getName() + ":" + fileInfo.getUrl();
    }
  }

  public static class FileInfo {

    private String url;

    private String name;

    FileInfo(String url, String name) {
      this.url = url;
      this.name = name;
    }

    public String getUrl() {
      return url;
    }

    public String getName() {
      return name;
    }

  }

}
