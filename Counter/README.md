# Counter アプリ学習メモ

このプロジェクトは、Jetpack Compose で作るシンプルなプラスマイナスカウンターです。

初学者向けに、今のコードが

- アプリ起動からどう動くか
- なぜその書き方をしているか
- `MutableStateFlow` と `mutableStateOf` の違い

をまとめています。

## 今の構成

- [`MainActivity.kt`](/Users/yoshiokayuki/src/Android/Counter/app/src/main/java/com/example/counter/MainActivity.kt)
  アプリの入口
- [`MainViewModel.kt`](/Users/yoshiokayuki/src/Android/Counter/app/src/main/java/com/example/counter/MainViewModel.kt)
  カウント状態と加算減算のルールを持つ

考え方はシンプルです。

- `Activity` は画面を起動する入口
- `ViewModel` は状態を持つ場所
- `Composable` は見た目を描く場所

## 処理の流れ

### 1. アプリが起動する

最初に `MainActivity` が動きます。

```kotlin
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
```

ここで `MainViewModel` を作っています。

`by viewModels()` を使う理由は、画面で使う状態を `Activity` と結びつけて安全に管理するためです。

### 2. `setContent` で Compose の画面を作る

```kotlin
setContent {
    CounterTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                MainScreen(viewModel = mainViewModel)
            }
        }
    }
}
```

ここでは次の役割があります。

- `CounterTheme`
  アプリ全体の見た目をまとめる
- `Scaffold`
  Material Design の画面の土台
- `Box`
  画面全体の領域を確保して、中央配置しやすくする器
- `MainScreen`
  実際の表示を担当する関数

### 3. `MainScreen` が ViewModel の値を読む

```kotlin
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val count = viewModel.count
```

今は `MainViewModel` 側で `count` を `mutableStateOf` で持っています。

そのため `MainScreen` で `viewModel.count` を読むだけで、`count` が変わった時に Compose が再描画してくれます。

### 4. `Box` と `Column` で配置する

```kotlin
Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
```

ここでやっていることは2つです。

- `Box`
  画面全体の中央にコンテンツを置く
- `Column`
  タイトル、件数、ボタンを縦に並べる

### 5. 画面に count を表示する

```kotlin
Text(text = "${count}件")
```

ここで表示している `count` は `ViewModel` の状態です。

つまり画面は、自分で数字を持っているのではなく、`ViewModel` の値を見ているだけです。

### 6. ボタンはイベントだけを外へ渡す

```kotlin
CounterButtons(
    onIncrement = viewModel::increment,
    onDecrement = viewModel::decrement,
)
```

`CounterButtons` 自体は、加算減算のルールを持っていません。

持っているのは

- `+` が押されたら何をするか
- `-` が押されたら何をするか

だけです。

この形にすると、見た目の部品と状態変更の処理を分けられます。

### 7. 実際の増減処理は ViewModel にある

```kotlin
class MainViewModel : ViewModel() {
    var count by mutableIntStateOf(0)
        private set

    fun increment() {
        count += 1
    }

    fun decrement() {
        if (count > 0) {
            count -= 1
        }
    }
}
```

ここでは次のルールを持っています。

- 初期値は `0`
- `+` で `1` 増やす
- `-` で `1` 減らす
- `0` 未満にはしない

`private set` をつけている理由は、外から勝手に `count` を書き換えさせないためです。

## なぜ `ViewModel` に状態を置くのか

今回のような小さなアプリでも、状態を `ViewModel` にまとめておくと役割がきれいに分かれます。

- `UI`
  表示する
- `ViewModel`
  状態を持つ、ルールを持つ

この分け方にしておくと、あとから

- 履歴追加
- DB 保存
- API 通信

のような拡張がしやすくなります。

## `MutableStateFlow` と `mutableStateOf` の違い

### `mutableStateOf`

Compose 用のシンプルな状態です。

```kotlin
var count by mutableIntStateOf(0)
    private set
```

特徴は次の通りです。

- Compose でそのまま読みやすい
- 学習の最初に理解しやすい
- 小さい画面状態に向いている

Composable からはそのまま読めます。

```kotlin
val count = viewModel.count
```

### `MutableStateFlow`

Kotlin Flow ベースの状態です。

```kotlin
private val _count = MutableStateFlow(0)
val count: StateFlow<Int> = _count
```

こちらは Compose 専用ではないので、画面側では変換が必要です。

```kotlin
val count by viewModel.count.collectAsStateWithLifecycle()
```

特徴は次の通りです。

- Flow に慣れられる
- Compose 以外ともつなぎやすい
- 実務でよく見る構成に寄せやすい

## 今回 `mutableStateOf` に戻した理由

今回は学習優先なので、`mutableStateOf` に戻しています。

理由はシンプルです。

- `collectAsStateWithLifecycle()` をまだ覚えなくてよい
- `count` が変わると再描画される流れを追いやすい
- 「状態を読むと UI が変わる」という Compose の基本を理解しやすい

まずは `mutableStateOf` で

1. 状態を持つ
2. 画面で読む
3. ボタンで更新する
4. 再描画される

を理解するのがよいです。

そのあとで `StateFlow` に進むと、違いがかなり分かりやすくなります。

## 今のコードで覚えるとよいポイント

- `Modifier.fillMaxSize()`
  要素自身を親いっぱいに広げる
- `Modifier.fillMaxWidth()`
  横幅だけ親いっぱいに広げる
- `horizontalAlignment`
  `Column` の子要素を左右どこに置くか
- `verticalAlignment`
  `Row` の子要素を上下どこに揃えるか
- `horizontalArrangement`
  `Row` の中で横方向にどう並べるか

## まとめ

今のアプリは次の流れで動いています。

1. `MainActivity` が起動する
2. `MainViewModel` を用意する
3. `MainScreen` に ViewModel を渡す
4. `MainScreen` が `count` を読む
5. ボタンを押す
6. `ViewModel` の `count` が変わる
7. Compose が再描画する
8. 画面の数字が更新される

最初は `mutableStateOf` で十分です。

Compose の基本に慣れてきたら、次に `StateFlow` へ進むのが自然です。
