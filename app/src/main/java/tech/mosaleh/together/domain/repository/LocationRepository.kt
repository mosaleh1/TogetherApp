package tech.mosaleh.together.domain.repository

import com.google.android.gms.maps.model.LatLng

interface LocationRepository {

    fun updateLastKnownLocation(userId: String, location: LatLng)

}