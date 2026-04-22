package com.example.expensetracker.data.local

import androidx.room.TypeConverter

// Currently empty of real converters because our schema uses only:
// Long, Double, String — all natively supported by Room.
//
// We store Category as String (categoryName) in the entity,
// so no converter needed for that either.
//
// WHEN YOU WILL NEED THIS:
// If you add a List<String> field (like receipt photo paths), add:

class Converters {

// Example — storing a list of tags as comma-separated String:
//
// @TypeConverter
// fun fromTagList(tags: List<String>): String = tags.joinToString(",")
// @TypeConverter
//    // fun toTagList(raw: String): List<String> =
//    //     if (raw.isBlank()) emptyList() else raw.split(",")
//    //
//    // Keep converters dead simple. No business logic here.
//}