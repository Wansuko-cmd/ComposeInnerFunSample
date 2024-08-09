# 概要

Composeにて関数内関数を使った場合、Skippableになるかどうかを調査するためのリポジトリです

# 検証コード

```kotlin
@Composable
fun SampleList(text: String) {
    @Composable
    fun SampleItem1() {
        Log.d("SampleItem1", "Recomposed!")
        Text(text = "SampleItem1")
    }
    SampleItem1()
    SampleItem2()

    // SampleListのRecomposeを起こすため
    Text(text = text)
}

@Composable
fun SampleItem2() {
    Log.d("SampleItem2", "Recomposed!")
    Text(text = "SampleItem2")
}
```

これでSampleListの引数を変更することでRecomposeを発生させて挙動を見てみる

# Compose Compiler Metrics

```txt
scheme("[androidx.compose.ui.UiComposable]") fun SampleItem1()
restartable skippable scheme("[androidx.compose.ui.UiComposable]") fun SampleList(
  stable text: String
)
restartable skippable scheme("[androidx.compose.ui.UiComposable]") fun SampleItem2()

```

`SampleItem1`はrestartableではなければskippableでもなさそう

# 実行するとどうなるか

SampleItem1のみ`Recomosed!`のログが出力される

https://github.com/user-attachments/assets/ce8fa1df-e1f5-415d-841d-16ad69180f18

# Layout Inspector

`SampleItem1`、`SampleItem2`の両方とも同じ挙動をとる

https://github.com/user-attachments/assets/bb24d849-7576-4792-8758-f35c4508b64d

# どのように解釈されているのか？

生成されるバイトコードをJavaにデコンパイルすると以下のような定義になっているっぽい

```java
   public static final void SampleList(@NotNull final String text, @Nullable Composer $composer, final int $changed)

   private static final void SampleList$SampleItem1(Composer $composer, int $changed)

   public static final void SampleItem2(@Nullable Composer $composer, final int $changed)
```

- 命名の頭に`SampleList$`がつく（多分そこまで変わらない）
- `changed`の値がfinalじゃなくなる（こっちが問題？）
