# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#反射使用的元素，必须保证类名、方法名、属性名不变，否则混淆后会反射不了
#参考：https://blog.csdn.net/calm1516/article/details/82999381，但是不生效，为什么！！！！
-keep class workshop1024.com.xproject.home.controller.fragment.HomePageFragment { *; }
-keep class workshop1024.com.xproject.home.controller.fragment.HomePageFragment$Companion { *; }
