package tech.mosaleh.together.domain.use_cases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GetCompleteAddressUseCase(
    private val application: Context
) {

    suspend operator fun invoke(lat: Double, lng: Double): String {
        var address: String
        withContext(Dispatchers.IO) {
            address = getCompleteAddress(lat, lng)
        }
        return address
    }

    private fun getCompleteAddress(lat: Double, lng: Double): String {
        var addressStr = ""
        val geocoder = Geocoder(application.applicationContext, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                addressStr = strReturnedAddress.toString()
            } else {
                Log.d("TAG", "getCompleteAddressString:  ")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //Log.v("My Current location address", "Cannot get Address!")
        }
        return addressStr
    }
}
