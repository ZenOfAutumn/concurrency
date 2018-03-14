package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;

public class DataProducer {

  private final RingBuffer<Data> ringBuffer;

  public DataProducer(RingBuffer<Data> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void pushData(ByteBuffer buffer) {
    long sequence = ringBuffer.next();
    try {
      Data data = ringBuffer.get(sequence);
      data.setValue(buffer.getLong(0));
      data.setThreadName(Thread.currentThread().getName());
    } finally {
      ringBuffer.publish(sequence);
    }
  }

}
