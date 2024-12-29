package com.cursee.monolib.util.toml.important;

import java.util.TimeZone;

public class DatePolicy {

  private final TimeZone timeZone;
  private final boolean showFractionalSeconds;
  
  public DatePolicy(TimeZone timeZone, boolean showFractionalSeconds) {
    this.timeZone = timeZone;
    this.showFractionalSeconds = showFractionalSeconds;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public boolean isShowFractionalSeconds() {
    return showFractionalSeconds;
  }
}
