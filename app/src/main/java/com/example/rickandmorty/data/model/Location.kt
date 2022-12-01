package com.example.rickandmorty.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id"        ) var id: Int?              = null,
    @SerializedName("name"      ) var name: String?           = null,
    @SerializedName("type"      ) var type: String?           = null,
    @SerializedName("dimension" ) var dimension: String?           = null,
    @SerializedName("residents" ) var residents: List<String>? = listOf(),
    @SerializedName("url"       ) var url: String?           = null,
    @SerializedName("created"   ) var created: String?           = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(dimension)
        parcel.writeStringList(residents)
        parcel.writeString(url)
        parcel.writeString(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}