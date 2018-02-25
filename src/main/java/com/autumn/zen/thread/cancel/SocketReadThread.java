package com.autumn.zen.thread.cancel;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Socket Read Thread Support Interrupt by Close Socket
 **/
public class SocketReadThread extends Thread {

  private final Socket socket;

  private final InputStream in;

  SocketReadThread(Socket socket, InputStream in) {
    this.socket = socket;
    this.in = in;
  }

  @Override
  public void interrupt() {
    try {
      socket.close();
    } catch (IOException e) {
      // ignore
    } finally {
      super.interrupt();
    }
  }

  @Override
  public void run() {

    try {
      byte[] buff = new byte[100];
      while (true) {
        int count = in.read(buff);
        if (count < 0) {
          break;
        } else if (count > 0) {
          TimeUnit.SECONDS.sleep(2);//mock process
        }
      }
    } catch (IOException | InterruptedException e) {
      // exit
    }

  }
}
