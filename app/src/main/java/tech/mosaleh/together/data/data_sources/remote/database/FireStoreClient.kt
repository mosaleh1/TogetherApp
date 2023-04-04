package tech.mosaleh.together.data.data_sources.remote.database

import com.google.android.gms.maps.model.LatLng
import tech.mosaleh.together.domain.model.User

interface FireStoreClient {

    fun updateLastKnownLocation(userId: String, location: LatLng)
    fun uploadUserToFireStore(user: User)
    fun uploadFCMToken(substring: String)
}