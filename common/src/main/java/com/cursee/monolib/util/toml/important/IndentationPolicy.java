package com.cursee.monolib.util.toml.important;

import com.cursee.monolib.util.toml.TomlWriter;

/**
 * Controls how a {@link TomlWriter} indents tables and key/value pairs.
 *
 * The default policy is to not indent.
 */
public class IndentationPolicy {
  private final int tableIndent;
  private final int keyValueIndent;
  private final int arrayDelimiterPadding;

  public IndentationPolicy(int keyIndentation, int tableIndentation, int arrayDelimiterPadding) {
    this.keyValueIndent = keyIndentation;
    this.tableIndent = tableIndentation;
    this.arrayDelimiterPadding = arrayDelimiterPadding;
  }

  public int getTableIndent() {
    return tableIndent;
  }

  public int getKeyValueIndent() {
    return keyValueIndent;
  }

  public int getArrayDelimiterPadding() {
    return arrayDelimiterPadding;
  }
}
