# Opentracing thread-local propagation

[![Build Status](https://travis-ci.org/lucidsoftware/opentracing-thread-context.svg?branch=master)](https://travis-ci.org/lucidsoftware/opentracing-thread-context)
![Maven Version](https://img.shields.io/maven-central/v/com.lucidchart/opentracing-thread-context.svg)

In-process thread-local propagation of spans.

## Usage

```java
import io.opentracing.Span;
import io.opentracing.threadcontext.ThreadContextSpan;

Span span = ...
ThreadContextSpan.withSpan(span, () -> {
    ThreadContextSpan.get(); // current Span
});
```
