package tech.mosaleh.together.data.repository

import com.google.android.gms.maps.model.LatLng
import tech.mosaleh.together.data.data_sources.remote.database.FireStoreClient
import tech.mosaleh.together.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val client: FireStoreClient
) : LocationRepository {
    override fun updateLastKnownLocation(userId: String, location: LatLng) {
        client.updateLastKnownLocation(userId, location)
    }
}