package io.opentracing.threadcontext;

import com.github.threadcontext.Context;
import com.github.threadcontext.MutableContextSupplier;
import com.github.threadcontext.ThreadLocalContextSupplier;
import com.github.threadcontext.control.TryFinallyContext;
import io.opentracing.noop.NoopSpan;
import io.opentracing.Span;

public class ContextSpan {

    public static final ContextSpan DEFAULT = new ContextSpan(Context.DEFAULT);

    private final ThreadLocal<Span> span;

    public ContextSpan(MutableContextSupplier contextSupplier) {
        span = new ThreadLocal<Span>() {
            protected Span initialValue() {
                return NoopSpan.INSTANCE;
            }
        };
        contextSupplier.suppliers.add(new ThreadLocalContextSupplier<>(span));
    }

    public Span get() {
        return span.get();
    }

    public Context set(Span span) {
        return new TryFinallyContext(() -> {
            Span oldValue = this.span.get();
            this.span.set(span);
            return () -> this.span.set(oldValue);
        });
    }

}
