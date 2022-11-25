package com.example.rickandmorty.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id"         ) var id         : Int?              ,
    @SerializedName("name"       ) var name       : String?           ,
    @SerializedName("air_date"   ) var airDate    : String?           ,
    @SerializedName("episode"    ) var codeOfEpisode    : String?           ,
    @SerializedName("characters" ) var characters : ArrayList<String>?,
    @SerializedName("url"        ) var url        : String?           ,
    @SerializedName("created"    ) var created    : String?           ,


):Parcelable {
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
        parcel.writeString(airDate)
        parcel.writeString(codeOfEpisode)
        parcel.writeStringList(characters)
        parcel.writeString(url)
        parcel.writeString(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Episode> {
        override fun createFromParcel(parcel: Parcel): Episode {
            return Episode(parcel)
        }

        override fun newArray(size: Int): Array<Episode?> {
            return arrayOfNulls(size)
        }
    }
}
