package io.opentracing.threadcontext;

import com.github.threadcontext.ThreadContext;
import com.github.threadcontext.ThreadLocalSaver;
import io.opentracing.NoopSpan;
import io.opentracing.Span;
import java.util.function.Supplier;

public final class ThreadContextSpan {

    private static final ThreadLocal<Span> span = new ThreadLocal<Span>() {
        protected Span initialValue() {
            return defaultSpan.get();
        }
    };
    static {
        ThreadContext.savers.add(new ThreadLocalSaver<>(span));
    }

    public static Supplier<Span> defaultSpan = () -> NoopSpan.INSTANCE;

    private ThreadContextSpan() {
    }

    public static Span get() {
        return span.get();
    }

    public static void set(Span span) {
        ThreadContextSpan.span.set(span);
    }

    public static void clear() {
        span.remove();
    }

}
