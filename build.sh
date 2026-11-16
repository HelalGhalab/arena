#!/usr/bin/env bash
# Build script - no Gradle needed. Usage: ./build.sh <android-sdk-path>
set -e
SDK=${1:?usage: ./build.sh /path/to/android-sdk}
BT=$(ls -d "$SDK"/build-tools/* | sort -V | tail -1)
AJ=$(ls -d "$SDK"/platforms/android-*/android.jar | sort -V | tail -1)
rm -rf build && mkdir -p build/classes
javac -encoding UTF-8 -source 8 -target 8 -bootclasspath "$AJ" -cp "$AJ" -d build/classes src/com/helal/tasbih/MainActivity.java
"$BT/d8" --release --min-api 21 --lib "$AJ" --output build build/classes/com/helal/tasbih/*.class
"$BT/aapt2" link -o build/base.apk -I "$AJ" --manifest AndroidManifest.xml --min-sdk-version 21 --target-sdk-version 35
( cd build && zip -q -j -0 base.apk classes.dex )
"$BT/zipalign" -f -p 4 build/base.apk build/aligned.apk
[ -f keystore.jks ] || keytool -genkeypair -keystore keystore.jks -alias arena -keyalg RSA -keysize 2048 -validity 10950 -storepass arena123 -keypass arena123 -dname "CN=Arena Tasbih, O=Arena, C=YE"
"$BT/apksigner" sign --ks keystore.jks --ks-pass pass:arena123 --key-pass pass:arena123 --v1-signing-enabled true --v2-signing-enabled true --out ArenaTasbih.apk build/aligned.apk
ls -l ArenaTasbih.apk
