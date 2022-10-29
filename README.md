# KeepMemoAndroid　Application
これはKeepMemoAndroidのレポジトリです。

KeepMemoAndroidは、100% **Kotlin** と **Jetpack Compose** で構築された、メモアプリです。

## 特徴
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

## Materil3（M3）対応
Material3に対応しました（2022/10/29）

Android12以上の端末で、Material Youに対応しています

## 使用しているライブラリ

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

## スクリーンショット

|![Screenshot_20221029-141413](https://user-images.githubusercontent.com/37167834/198816197-90dce303-d42d-4c19-aeb9-2aec18643023.png)|![Screenshot_20221029-141505](https://user-images.githubusercontent.com/37167834/198816202-b2b08a1d-18b1-4201-ae05-72866d97ed19.png)|![Screenshot_20221029-141513](https://user-images.githubusercontent.com/37167834/198816234-acdae3fb-80ce-416a-804f-fc87ce5da0c7.png)|![Screenshot_20221029-141522](https://user-images.githubusercontent.com/37167834/198816241-09fe1d9a-5434-4750-bb7b-baa6ac1f1d01.png)|
---|---|---|---|
