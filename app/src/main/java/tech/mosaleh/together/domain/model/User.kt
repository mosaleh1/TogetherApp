package tech.mosaleh.together.domain.model

import com.google.android.gms.maps.model.LatLng

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val lastKnownLocation: LatLng = LatLng(0.0, 0.0)
)