package tech.mosaleh.together.domain.model

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CaseAddress(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    var addressStr: String = "",
) : Parcelable {

    fun isValid(): Boolean {
        return lat != 0.0 &&
                lng != 0.0 &&
                addressStr.isNotBlank()
    }
}
