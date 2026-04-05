# SimpleTodoApp メモ

## Hilt / Room 依存追加手順

別の Android プロジェクトで、今回と同じように `Hilt` と `Room` を入れるときの最小手順メモです。

## 前提

- Kotlin + Compose の Android アプリ
- 依存管理は `version catalog` (`gradle/libs.versions.toml`)
- Annotation Processor は `KSP` を利用

今回このプロジェクトで使ったバージョン:

- `Hilt: 2.59.2`
- `Room: 2.8.4`
- `KSP: 2.2.10-2.0.2`

最新バージョンは都度公式で確認すること:

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=ja)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## 1. `libs.versions.toml` に追加する

[gradle/libs.versions.toml](./gradle/libs.versions.toml)

```toml
[versions]
hilt = "2.59.2"
hiltNavigationCompose = "1.3.0"
ksp = "2.2.10-2.0.2"
room = "2.8.4"

[libraries]
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

## 2. ルート `build.gradle.kts` に plugin を追加する

[build.gradle.kts](./build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
}
```

## 3. `app/build.gradle.kts` に plugin を追加する

[app/build.gradle.kts](./app/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}
```

## 4. `app/build.gradle.kts` に dependency を追加する

[app/build.gradle.kts](./app/build.gradle.kts)

```kotlin
dependencies {
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
}
```

補足:

- `androidx.hilt.navigation.compose` は Compose Navigation と組み合わせるときに便利
- `room-ktx` を入れておくと `Flow` や coroutine と相性がよい
- `kapt` ではなく `ksp` でそろえるとシンプル

## 5. `gradle.properties` の注意点

今回の環境では `AGP 9 + built-in Kotlin + KSP` の組み合わせで、追加直後のビルド時にエラーが出ました。

[gradle.properties](./gradle.properties)

```properties
android.disallowKotlinSourceSets=false
```

この設定を追加してビルドを通しました。

注意:

- これは experimental な回避設定
- 今後 AGP / Kotlin / KSP の組み合わせ次第で不要になる可能性あり
- 別プロジェクトで入れるときは、まずビルドして必要かどうか確認する

## 6. 確認ビルド

依存追加後は最低限これを実行:

```bash
./gradlew assembleDebug
```

今回のプロジェクトではこのコマンドで `BUILD SUCCESSFUL` を確認済みです。

## 7. 依存追加の次にやること

依存を入れただけではまだ使えないので、通常は次も必要になります。

- Hilt 用の `Application` クラス作成
- `@HiltAndroidApp` の付与
- `AndroidManifest.xml` への `application` 指定
- Room の `Entity`, `Dao`, `Database` 作成
- Hilt の `Module` で `Database`, `Dao`, `Repository` を提供

入門用なら、まずはここまでを最小構成で作るのがおすすめです。
