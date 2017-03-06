package io.opentracing.threadcontext;

import com.github.threadcontext.Saver;
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
    private static final Saver saver = new ThreadLocalSaver<>(span);
    static {
        ThreadContext.savers.add(saver);
    }

    public static Supplier<Span> defaultSpan = () -> NoopSpan.INSTANCE;

    private ThreadContextSpan() {
    }

    public static Span get() {
        return span.get();
    }

    public static void withSpan(Span span, Runnable runnable) {
        saver.runAndRestore(() -> {
            ThreadContextSpan.span.set(span);
            runnable.run();
        });
    }

}
