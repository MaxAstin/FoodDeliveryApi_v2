package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapSuggestionsResponse
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressRequestBody
import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import com.bunbeauty.fooddelivery.network.getDataOrNull
import java.util.*

private const val STREET_BOUND = "street"

class AddressRepository(
    private val addressNetworkDataSource: AddressNetworkDataSource,
    private val addressDao: AddressDao,
) {

    suspend fun insertAddress(insertAddress: InsertAddress): Address {
        return addressDao.insertAddress(insertAddress)
            .mapAddressEntity()
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<Address> {
        return addressDao.getAddressListByUserUuid(userUuid = userUuid)
            .filter { addressEntity ->
                addressEntity.street.cafe.city.id.value == cityUuid
            }.map(mapAddressEntity)
    }

    suspend fun getStreetSuggestionList(query: String, city: City): List<String> {
        return addressNetworkDataSource.requestAddressSuggestions(
            AddressRequestBody(
                query = query,
                fromBound = STREET_BOUND,
                toBound = STREET_BOUND,
                locations = city.name,
            )
        ).getDataOrNull()
            ?.mapSuggestionsResponse()
            .orEmpty()
    }

}