# arena — مسبحة (Tasbih Counter)

تطبيق أندرويد صغير جدًا (≈17 كيلوبايت) لعدّ التسبيح. زر واحد: اضغط في أي مكان على الشاشة فيزيد الرقم.

- **الحجم:** ~17 KB
- **بدون ويب، بدون Google Play، بدون مكتبات خارجية** (Pure Java + Android Framework فقط)
- **minSdk 21** (أندرويد 5.0+) — **targetSdk 35**
- يعمل على **arm64-v8a** وجميع المعماريات (لا يحتوي كود Native، لذا APK واحد يعمل على كل الأجهزة)
- يحفظ العدد تلقائيًا عند إغلاق التطبيق
- عدّاد دورات 33 مع تصفير يدوي + اهتزاز خفيف عند كل ضغطة

## التحميل
من صفحة الإصدارات: `releases/latest`

## البناء من المصدر (بدون Gradle)
```bash
./build.sh /path/to/android-sdk
```
أو يدويًا:
```bash
javac -source 8 -target 8 -bootclasspath $SDK/platforms/android-35/android.jar -d build/classes src/com/helal/tasbih/MainActivity.java
d8 --release --min-api 21 --lib $SDK/platforms/android-35/android.jar --output build build/classes/com/helal/tasbih/*.class
aapt2 link -o build/base.apk -I $SDK/platforms/android-35/android.jar --manifest AndroidManifest.xml --min-sdk-version 21 --target-sdk-version 35
cd build && zip -j -0 base.apk classes.dex
zipalign -f -p 4 base.apk aligned.apk
apksigner sign --ks ../keystore.jks --ks-pass pass:arena123 --out ../ArenaTasbih.apk aligned.apk
```

## التثبيت على الهاتف
1. حمّل ملف `ArenaTasbih.apk`
2. افتحه واسمح بـ «تثبيت من مصادر غير معروفة» إن طُلب
3. ثبّت وافتح — واضغط للتسبيح
