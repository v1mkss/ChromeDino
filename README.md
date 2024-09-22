# Build Options

Choose one of the following build options:

1. **Package**: This is the default build option. It creates a JAR file that can be run on any JVM.

2. **Native Image**: This option builds a native executable using GraalVM Native Image. It results in faster startup times and lower memory usage but may have limitations on certain Java features.

To use Native Image, ensure you have GraalVM installed and configured. Then, uncomment and set the `graalVMNativeImageCommand` in your `build.sbt` file:

```sbt
graalVMNativeImageCommand := "/path/to/graalvm/bin/native-image"
```

Note: If you're not using Native Image, you can leave this line commented out or remove it entirely.
