# GoodButtonCounter

Jetpack Compose で作ったシンプルなリアクションカウンターアプリです。  
`いいね` と `あんまり` をボタンで記録し、`リセット` で 0 に戻せます。

## できること

- `いいね` ボタンで `goodCount` を 1 増やす
- `あんまり` ボタンで `badCount` を 1 増やす
- `リセット` ボタンで両方のカウントを 0 に戻す
- 現在のカウントを画面中央のパネルに表示する

## UI のポイント

- 画面中央にカード風のレイアウトを配置
- 背景にテーマ色ベースの淡いグラデーションを適用
- `いいね` と `あんまり` の数値を `Surface` で分けて見やすく表示
- 3つのボタンは横並びで、同じ幅になるように調整

## 使用している主な技術

- Kotlin
- Jetpack Compose
- Material 3
- ViewModel

## ファイル構成

- `app/src/main/java/com/example/goodbuttoncounter/MainActivity.kt`
  画面表示と UI レイアウト
- `app/src/main/java/com/example/goodbuttoncounter/MainViewModel.kt`
  `goodCount` / `badCount` の state 管理とボタン処理

## 状態管理の考え方

カウントの state は `MainViewModel` に持たせています。  
`MainActivity` 側では `viewModel()` で取得し、`MainScreen` に渡して表示とクリック処理を行っています。

### `setContent` の中で `viewModel()` を使う理由

`viewModel()` は Compose の中で使う取得関数なので、`setContent { ... }` の中で呼ぶ必要があります。  
この位置で取得すると、画面表示と同じスコープで `ViewModel` を扱えて、再コンポーズが発生しても同じ `ViewModel` を使い続けられます。

### ハマりやすいポイント

Compose で `viewModel()` を使うときは、`build.gradle.kts` に依存関係を追加し忘れやすいです。  
`Unresolved reference 'viewModel'` のようなエラーが出たら、まず次を確認します。

- `import androidx.lifecycle.viewmodel.compose.viewModel`
- `implementation("androidx.lifecycle:lifecycle-viewmodel-compose")`

## 今後の発展案

- 合計リアクション数の表示
- ボタンを押した回数の履歴表示
- Todo アプリのような複数画面構成への拡張
- `MainScreen` を state と event 受け取り型にして、さらに責務を分離
