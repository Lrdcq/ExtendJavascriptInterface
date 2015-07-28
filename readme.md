### This is a small class for Android to be called by javascript safety under Android 4.2!
### See the example Activity to know how to use it.

also put a this:

- package android.webkit;

- import java.lang.annotation.ElementType;
- import java.lang.annotation.Retention;
- import java.lang.annotation.RetentionPolicy;
- import java.lang.annotation.Target;

- @Retention(RetentionPolicy.RUNTIME)
- @Target({ElementType.METHOD})
- public @interface JavascriptInterface {}

