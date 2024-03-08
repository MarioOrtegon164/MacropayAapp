package com.macropay.prueba.data.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName




data class MyDataModel(val id: Int, val name: String, val description: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyDataModel> {
        override fun createFromParcel(parcel: Parcel): MyDataModel {
            return MyDataModel(parcel)
        }

        override fun newArray(size: Int): Array<MyDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
