package com.cursee.monolib.util.toml;

import com.cursee.monolib.util.toml.important.Context;
import com.cursee.monolib.util.toml.important.ValueReader;
import com.cursee.monolib.util.toml.important.ValueWriter;
import com.cursee.monolib.util.toml.important.WriterContext;

import java.util.concurrent.atomic.AtomicInteger;


public class BooleanValueReaderWriter implements ValueReader, ValueWriter {
  
  public static final BooleanValueReaderWriter BOOLEAN_VALUE_READER_WRITER = new BooleanValueReaderWriter();

  @Override
  public boolean canRead(String s) {
    return s.startsWith("true") || s.startsWith("false");
  }

  @Override
  public Object read(String s, AtomicInteger index, Context context) {
    s = s.substring(index.get());
    Boolean b = s.startsWith("true") ? Boolean.TRUE : Boolean.FALSE;
    
    int endIndex = b == Boolean.TRUE ? 4 : 5;
    
    index.addAndGet(endIndex - 1);
    
    return b;
  }

  @Override
  public boolean canWrite(Object value) {
    return Boolean.class.isInstance(value);
  }

  @Override
  public void write(Object value, WriterContext context) {
    context.write(value.toString());
  }

  @Override
  public boolean isPrimitiveType() {
    return true;
  }

  private BooleanValueReaderWriter() {}

  @Override
  public String toString() {
    return "boolean";
  }
}
