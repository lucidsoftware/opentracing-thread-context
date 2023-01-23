package io.opentracing.threadcontext;

import com.github.threadcontext.Context;
import com.github.threadcontext.MutableContextSupplier;
import com.github.threadcontext.ThreadLocalContextSupplier;
import com.github.threadcontext.control.TryFinallyContext;
import io.opentracing.Scope;
import io.opentracing.noop.NoopSpan;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class ContextSpan {

    public static final ContextSpan DEFAULT = new ContextSpan(Context.DEFAULT);

    private final ThreadLocal<Span> span;
    // Only unclosed scopes are persisted here.
    private final ThreadLocal<Scope> scope;
    private final Tracer tracer;

    public ContextSpan(MutableContextSupplier contextSupplier) {
        this(contextSupplier, GlobalTracer.get());
    }

    public ContextSpan(MutableContextSupplier contextSupplier, Tracer customTracer) {
        tracer = customTracer;
        span = new ThreadLocal<Span>() {
            protected Span initialValue() {
                return NoopSpan.INSTANCE;
            }
        };
        scope = new ThreadLocal<Scope>();
        contextSupplier.suppliers.add(new ThreadLocalContextSupplier<>(span));
        contextSupplier.suppliers.add(new ThreadLocalContextSupplier<>(scope));
    }

    public Span get() {
        return span.get();
    }

    public Context set(Span span) {
        return new TryFinallyContext(() -> {
            Span oldValue = this.span.get();
            Scope oldScope = this.scope.get();
            if (oldScope != null) {
                oldScope.close();
                this.scope.set(null);
            }
            this.span.set(span);
            Scope scope = this.tracer.activateSpan(span);
            return () -> {
                scope.close();
                this.span.set(oldValue);
                if (oldValue != NoopSpan.INSTANCE) {
                    Scope newScope = this.tracer.activateSpan(oldValue);
                    this.scope.set(newScope);
                }
            };
        });
    }

}
