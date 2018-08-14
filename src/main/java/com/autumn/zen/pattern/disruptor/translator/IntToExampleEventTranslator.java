package com.autumn.zen.pattern.disruptor.translator;

import com.lmax.disruptor.EventTranslator;

public class IntToExampleEventTranslator implements EventTranslator<Long> {

  @Override
  public void translateTo(Long event, long sequence) {

  }
}
