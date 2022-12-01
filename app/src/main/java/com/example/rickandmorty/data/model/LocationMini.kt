package com.example.rickandmorty.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LocationMini(
    @SerializedName("name"      ) var name: String?           = null,
    @SerializedName("url"       ) var url: String?           = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationMini> {
        override fun createFromParcel(parcel: Parcel): LocationMini {
            return LocationMini(parcel)
        }

        override fun newArray(size: Int): Array<LocationMini?> {
            return arrayOfNulls(size)
        }
    }
}
