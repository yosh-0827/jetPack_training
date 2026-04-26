package com.example.shinmasclerecord.data

// object: 1回作ればよい共通データ置き場
object DummyWorkoutRecords {
    val homeRecords = listOf(
        WorkoutRecordEntity(
            id = 1,
            date = "2026-04-26",
            exerciseName = "ベンチプレス",
            weight = 60f,
            reps = 10,
            sets = 3,
            memo = "フォームを意識"
        ),
        WorkoutRecordEntity(
            id = 2,
            date = "2026-04-25",
            exerciseName = "スクワット",
            weight = 80f,
            reps = 8,
            sets = 3,
            memo = "深くしゃがむ"
        ),
        WorkoutRecordEntity(
            id = 3,
            date = "2026-04-24",
            exerciseName = "デッドリフト",
            weight = 100f,
            reps = 5,
            sets = 3,
            memo = "腰を丸めない"
        )
    )
}