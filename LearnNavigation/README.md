# LearnNavigation

Jetpack Compose の `Navigation` を最小構成で学ぶためのサンプルです。

今回の目的は、画面デザインや状態管理ではなく、`Navigation` の基本だけを理解することです。

## セットアップ

Navigation を使うには、最初に依存関係を追加します。

### 1. `gradle/libs.versions.toml` に追加する

`gradle/libs.versions.toml` に次を追加します。

```toml
[versions]
navigationCompose = "2.9.7"

[libraries]
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
```

ここで `navigation-compose` というライブラリを使えるようにしています。

### 2. `app/build.gradle.kts` に追加する

`app/build.gradle.kts` の `dependencies` に次を追加します。

```kotlin
implementation(libs.androidx.navigation.compose)
```

これで、このアプリで Compose Navigation を使えるようになります。

### 3. Sync する

依存関係を追加したあとは、Android Studio で Gradle Sync を行います。

Sync が終わると、次のような Navigation 用のコードが使えるようになります。

- `rememberNavController()`
- `NavHost`
- `composable`
- `navArgument`

## このアプリでやっていること

このアプリは 2 画面だけです。

- `Home` 画面を表示する
- ボタンを押すと `Detail` 画面へ移動する
- `itemId` を渡す
- `Detail` 画面でその値を表示する
- 戻るボタンで前の画面に戻る

## まず覚える言葉

### `navController`

画面移動を管理する役目です。

たとえば次のようなことをします。

- 別の画面へ移動する
- 前の画面へ戻る

### `NavHost`

アプリの中にどんな画面があるかを登録する場所です。

「最初にどの画面を出すか」もここで決めます。

### `composable`

1 つの画面を登録するための単位です。

`Home` 画面や `Detail` 画面を 1 つずつここで定義します。

## 画面構成

今回の構成は次の通りです。

```text
Home -> Detail
        ↑
        戻る
```

## コードの流れ

`app/src/main/java/com/example/learnnavigation/MainActivity.kt` では、上から順に次のことをしています。

### 1. 画面名を定義する

```kotlin
object SampleRoute {
    const val Home = "home"
    const val Detail = "detail/{itemId}"

    fun detail(itemId: Int): String = "detail/$itemId"
}
```

- `Home` はホーム画面の名前です
- `Detail` は詳細画面の名前です
- `{itemId}` は受け取る値の名前です
- `detail(1)` を呼ぶと `"detail/1"` という移動先文字列が作られます

### 2. アプリ起動時に `SampleApp()` を表示する

```kotlin
setContent {
    LearnNavigationTheme {
        SampleApp()
    }
}
```

ここで Compose の画面を表示しています。

今回の本体は `SampleApp()` です。

### 3. `navController` を作る

```kotlin
val navController = rememberNavController()
```

これで画面移動を管理するための部品を作っています。

### 4. `NavHost` で画面一覧を登録する

```kotlin
NavHost(
    navController = navController,
    startDestination = SampleRoute.Home
)
```

- `navController` を使って画面遷移を管理する
- 最初に表示する画面は `Home`

という意味です。

### 5. `Home` 画面を登録する

```kotlin
composable(route = SampleRoute.Home) {
    HomeScreen(
        onMoveDetail = {
            navController.navigate(SampleRoute.detail(itemId = 1))
        }
    )
}
```

- `Home` 画面を登録する
- ボタンが押されたら `Detail` 画面へ移動する
- そのとき `itemId = 1` を渡す

### 6. `Detail` 画面を登録する

```kotlin
composable(
    route = SampleRoute.Detail,
    arguments = listOf(
        navArgument("itemId") {
            type = NavType.IntType
        }
    )
) { backStackEntry ->
```

ここでは次のことを宣言しています。

- `Detail` 画面は `itemId` を受け取る
- `itemId` は `Int` 型

### 7. 渡された値を取り出す

```kotlin
val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
```

- `itemId` を取り出す
- もし取れなければ `0` を使う

### 8. 戻る処理を渡す

```kotlin
onBack = { navController.popBackStack() }
```

これは「1 つ前の画面に戻る」という意味です。

## `HomeScreen` と `DetailScreen` の役割

### `HomeScreen`

- `Home画面` の文字を表示する
- `Detail画面へ` ボタンを表示する
- ボタンが押されたら、受け取った `onMoveDetail()` を呼ぶ

### `DetailScreen`

- `Detail画面` の文字を表示する
- 渡された `itemId` を表示する
- `戻る` ボタンを押したら `onBack()` を呼ぶ

## 今回の学習で大事なポイント

- `Navigation` は「どの画面へ移動するか」を管理する仕組み
- `NavHost` に画面を登録する
- `startDestination` で最初の画面を決める
- `navigate()` で移動する
- `popBackStack()` で戻る
- 引数をつけて画面遷移できる

## 次にやると良さそうなこと

今回の内容が理解できたら、次は次の順番がおすすめです。

1. `itemId` を 1 固定ではなく、ボタンごとに変えてみる
2. `Home -> Detail -> Settings` の 3 画面にしてみる
3. 文字列ではなく `sealed interface` などでルートを整理してみる

## メモ

今は Navigation の理解を優先したいので、次のような要素はまだ入れなくて大丈夫です。

- ViewModel
- 画面ごとの状態管理
- データ保存
- 複雑な UI

まずは「画面が移動する」「値を渡せる」「戻れる」を確実に理解できれば十分です。
