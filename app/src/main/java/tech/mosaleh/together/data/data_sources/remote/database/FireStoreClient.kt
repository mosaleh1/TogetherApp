package tech.mosaleh.together.data.data_sources.remote.database

import tech.mosaleh.together.domain.model.User

interface FireStoreClient {

    fun updateUser(user: User)
}