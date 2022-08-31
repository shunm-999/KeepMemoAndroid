# KeepMemoAndroid　Application
これはKeepMemoAndroidのレポジトリです。

KeepMemoAndroidは、100% **Kotlin** と **Jetpack Compose** で構築された、メモアプリです。

# 特徴
アーキテクチャは、クリーンアーキテクチャを採用しており、
UIにはJetpack Composeを使用しています。

また、Hiltで依存関係注入を行い、コンポーネント間の独立性を高め、

開発効率をあげつつ、テストをしやすい構成にしています。

CI/CDには、Github Actionsを使用し、下記の作業を自動化しています。

- PRへのReviewerのアサイン
- PRへのラベルづけ
- PRされた内容の、静的解析、Android Lint実行
- 記載しているオープンライセンス情報に洩れがないか
- developブランチへのマージ後、Debugのapkビルド⇒DeployGateにdeploy
- リリース用のApp Bundle作成
- リリースノート作成

# 使用しているライブラリ

|  ライブラリ名  |  用途  |
| ---- | ---- |
|  Kotlin Flow  |  非同期処理  |
|  Jetpack Compose  |  UIの構築  |
|  Jetpack Compose Navigation  |  画面遷移  |
|  Jetpack Compose Accompanist  |  権限の要求 / WebViewの表示など  |
|Coil|画像の読み込み/表示|
|Room|DBの構築|
|Hilt|依存関係の注入|
|Timber|ロギング|
|Ktlint|フォーマット|
|Hyperion|アプリのデバッグ|

# スクリーンショット

|![スクリーンショット (24)](https://user-images.githubusercontent.com/37167834/184625222-df564616-ce0e-4f32-ae9b-b50a32e306ab.png)|![スクリーンショット (23)](https://user-images.githubusercontent.com/37167834/184625246-e88966ee-bbc8-4440-ac8c-38728ca206e9.png)|
---|---|
