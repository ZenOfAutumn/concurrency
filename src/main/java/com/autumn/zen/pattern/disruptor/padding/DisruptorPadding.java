package com.autumn.zen.pattern.disruptor.padding;

public class DisruptorPadding {

  private static class LhsPadding {

    protected long p1, p2, p3, p4, p5, p6, p7;
  }

  private static class Value extends LhsPadding {

    public volatile long value;
  }

  public static class RhsPadding extends Value {

    protected long p9, p10, p11, p12, p13, p14, p15;
  }
}
