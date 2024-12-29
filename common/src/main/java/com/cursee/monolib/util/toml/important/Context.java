package com.cursee.monolib.util.toml.important;

import java.util.concurrent.atomic.AtomicInteger;

public class Context {
  public final Identifier identifier;
  public final AtomicInteger line;
  public final Results.Errors errors;
  
  public Context(Identifier identifier, AtomicInteger line, Results.Errors errors) {
    this.identifier = identifier;
    this.line = line;
    this.errors = errors;
  }

  public Context with(Identifier identifier) {
    return new Context(identifier, line, errors);
  }
}
