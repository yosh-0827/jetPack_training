package com.example.simpletodoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
/**
 * DBアクセスの入り口。
 * これを窓口としてアプリ側からこのクラスをインスタンス化しDaoやEntityにアクセスできる。
 * ルーティングに近い。
 * */
@Database(
    entities = [TodoEntity::class],  // このDBでは TodoEntity を扱います
    version = 1,  // DBのバージョン
    exportSchema = false  // 学習用なので schema 出力はひとまず無効化
)
/**
 * なぜ abstract なのか
 * ここは最初ちょっと不思議ですが、Room が裏側で実装を作ってくれるからです。
 *
 * つまり自分では「こういうDBですよ」「このDaoがありますよ」
 * と宣言するだけで、実際の処理は Room が生成します。
 * Dao を interface にしたのと感覚が少し似ています。
 * */
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
