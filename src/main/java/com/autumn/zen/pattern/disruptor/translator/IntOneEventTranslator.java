package com.autumn.zen.pattern.disruptor.translator;

import com.lmax.disruptor.EventTranslatorOneArg;

public class IntOneEventTranslator implements EventTranslatorOneArg<Long, Integer> {

  @Override
  public void translateTo(Long event, long sequence, Integer arg0) {

  }
}
