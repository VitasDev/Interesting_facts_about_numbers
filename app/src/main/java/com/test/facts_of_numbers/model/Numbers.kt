package com.test.facts_of_numbers.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "number_table")
data class Numbers (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: Int,
    val factOfNumber: String
): Parcelable