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
# Keep application classes
-keep class com.colegiosociologosperu.cspmovillimacallao.** { *; }

# Keep ViewModel classes
-keep class androidx.lifecycle.ViewModel { *; }

# Keep Compose-related classes
-keep class androidx.compose.** { *; }

# Keep Hilt-generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# Keep Firebase-related classes
-keep class com.google.firebase.** { *; }
-keepattributes Signature

# Keep Retrofit and Gson models
-keep class com.colegiosociologosperu.cspmovillimacallao.models.** { *; }
-keepattributes *Annotation*

# Keep logging (optional)
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
# Hilt-specific rules
-keep class dagger.hilt.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.android.** { *; }
-keep class com.google.dagger.hilt.** { *; }
-keep @dagger.hilt.EntryPoint class * { *; }
-keep @dagger.hilt.InstallIn class * { *; }
-keep class **_HiltModules_* { *; }

#proguard
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**