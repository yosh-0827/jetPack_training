# 燃費計算

走行距離と給油量から、満タン法で車の燃費を計算するAndroidアプリです。

## 計算式

```text
燃費（km/L） = 走行距離（km） ÷ 給油量（L）
```

例として、500 km走行して40 L給油した場合、燃費は12.50 km/Lです。

## 主な機能

- 走行距離と給油量の入力
- 燃費の計算と小数第2位までの表示
- 未入力や0以下などの入力エラー表示
- 入力内容と結果のクリア
- ライトテーマ・ダークテーマ対応

入力値や計算結果の保存、履歴、通信機能はありません。

## 動作環境

- Android 7.0（API 24）以上
- JDK 17
- Android SDK 36

## 起動方法

1. Android Studioでこのプロジェクトを開きます。
2. Gradle Syncが完了するまで待ちます。
3. エミュレーターまたはAndroid端末を選択します。
4. `app`を実行します。

## テスト・ビルド

プロジェクトのルートで次のコマンドを実行します。

```shell
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
```

端末またはエミュレーターが接続されている場合は、UIテストも実行できます。

```shell
./gradlew connectedDebugAndroidTest
```

## 公開前の注意

現在のapplicationIdは仮の `com.example.carfuelcalc` です。Google Playへ初めて公開する前に、正式な一意のIDへ変更してください。
