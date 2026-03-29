# StopWatch

Jetpack Compose で作る、学習用のシンプルなタイマーアプリです。

今はまず、`30秒タイマー` を題材にして、画面表示とタイマーの動きを少しずつ実装していく段階です。

## 今の学習テーマ

- Compose でレイアウトを作る
- `ViewModel` で状態を持つ
- コルーチンで 1 秒ごとの処理を書く
- `Job` を使ってタイマー処理を管理する

## 状態の役割

`MainViewModel` では、主に次の状態を持ちます。

### `remainingSeconds`

```kotlin
var remainingSeconds by mutableStateOf(30)
    private set
```

- 今の残り時間です
- 画面にはこの値が表示されます
- `mutableStateOf(...)` にしているので、値が変わると Compose の画面も更新されます

今回の `30` は、まず動きを確認しやすくするための `30秒タイマー` の初期値です。

### `isRunning`

```kotlin
var isRunning by mutableStateOf(false)
    private set
```

- タイマーが動いているかどうかを表します
- `true` なら動作中、`false` なら停止中です
- `start()` を連打したときに二重でタイマーが動かないようにするためにも使います

### `initialSeconds`

```kotlin
private val initialSeconds = 30
```

- 最初の設定時間です
- `reset()` を作るときに、この値に戻すために使います

## コルーチンとは

コルーチンは、`時間のかかる処理をアプリを固めずに進めるための仕組み` です。

タイマーでは、1 秒待ってから数字を減らす処理が必要です。  
この「待つ処理」を普通のやり方で書くと、画面が止まりやすくなります。  
そこでコルーチンを使うと、画面を動かしたまま自然な書き方でタイマー処理を書けます。

```kotlin
viewModelScope.launch {
    while (isRunning && remainingSeconds > 0) {
        delay(1000)
        remainingSeconds--
    }
}
```

このコードは次の流れです。

1. コルーチンを開始する
2. 1 秒待つ
3. 残り秒数を 1 減らす
4. 時間が 0 になるまで繰り返す

### `delay(1000)` とは

```kotlin
delay(1000)
```

- 1000 ミリ秒 = 1 秒待つ、という意味です
- ただ止まるのではなく、`アプリ全体を固めずに待てる` のが大事なポイントです

### `viewModelScope.launch` とは

```kotlin
viewModelScope.launch { ... }
```

- `viewModelScope`
  - ViewModel にひもづいたコルーチンの範囲です
- `launch`
  - コルーチンを開始します

つまり、`ViewModel の中で安全に非同期処理を始める書き方` と考えると分かりやすいです。

## `Job` とは

```kotlin
private var timerJob: Job? = null
```

`Job` は、`今動いているコルーチンを表す管理用のオブジェクト` です。

分かりやすく言うと、`いま実行中の作業に付ける管理札` のようなものです。

`launch { ... }` を呼ぶと、その処理に対応する `Job` が返ってきます。

```kotlin
timerJob = viewModelScope.launch {
    ...
}
```

これで、開始したタイマー処理を `timerJob` に保存できます。

### なぜ `timerJob` が必要か

タイマーは、あとで止めたりリセットしたりしたくなります。  
そのとき、今動いている処理を特定できないと止められません。

そこで `timerJob` に保存しておくと、次のように止められます。

```kotlin
timerJob?.cancel()
```

- `timerJob` があれば、その処理を止める
- `?` があるので、`null` のときは何もしない

## `start()` の流れ

現在の `start()` は、次の意図で動いています。

```kotlin
fun start() {
    if (isRunning) return

    isRunning = true

    timerJob = viewModelScope.launch {
        while (isRunning && remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
    }
}
```

### 1. すでに動いていたら何もしない

```kotlin
if (isRunning) return
```

- 開始ボタンの連打で、タイマー処理が二重三重に動くのを防ぎます

### 2. 動作中の状態にする

```kotlin
isRunning = true
```

- タイマーが今動いていることを状態で管理します

### 3. コルーチンを開始する

```kotlin
timerJob = viewModelScope.launch {
```

- ここでタイマー処理をスタートします
- 戻り値の `Job` を `timerJob` に保存して、あとで止められるようにします

### 4. 1 秒ごとに残り時間を減らす

```kotlin
while (isRunning && remainingSeconds > 0) {
    delay(1000)
    remainingSeconds--
}
```

- `isRunning` が `true`
- かつ `remainingSeconds` が 0 より大きい

この条件の間だけ、1 秒待って 1 秒減らす処理を繰り返します。

## 今後追加したい処理

次のステップとしては、このあたりを追加するとタイマーとして完成に近づきます。

- `pause()` を作って一時停止できるようにする
- `reset()` を作って `initialSeconds` に戻せるようにする
- `00:30` のような表示形式に変える
- 30 秒ではなく、ポモドーロ用の時間に切り替える

## メモ

最初は `30秒` や `10秒` のような短い時間で試すと、動作確認がしやすいです。  
仕組みが分かってから `25 * 60` に変えると、ポモドーロタイマーへ発展させやすくなります。
