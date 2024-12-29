package com.cursee.monolib.util.toml;

import com.cursee.monolib.util.toml.important.ValueWriter;
import com.cursee.monolib.util.toml.important.WriterContext;

import java.util.Collection;

import static com.cursee.monolib.util.toml.important.ValueWriters.WRITERS;

public class TableArrayValueWriter extends ArrayValueWriter {

  public static final ValueWriter TABLE_ARRAY_VALUE_WRITER = new TableArrayValueWriter();

  @Override
  public boolean canWrite(Object value) {
    return isArrayish(value) && !isArrayOfPrimitive(value);
  }

  @Override
  public void write(Object from, WriterContext context) {
    Collection<?> values = normalize(from);

    WriterContext subContext = context.pushTableFromArray();

    for (Object value : values) {
      WRITERS.findWriterFor(value).write(value, subContext);
    }
  }

  private TableArrayValueWriter() {}

  @Override
  public String toString() {
    return "table-array";
  }
}
