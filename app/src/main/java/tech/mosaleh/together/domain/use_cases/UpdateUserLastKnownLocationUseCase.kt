package tech.mosaleh.together.domain.use_cases

import com.google.android.gms.maps.model.LatLng
import tech.mosaleh.together.domain.repository.LocationRepository

class UpdateUserLastKnownLocationUseCase(
    private val repository: LocationRepository
) {

    operator fun invoke(userId: String, location: LatLng) {
        repository.updateLastKnownLocation(userId, location)
    }
}