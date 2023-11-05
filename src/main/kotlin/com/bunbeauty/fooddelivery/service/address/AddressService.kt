package com.bunbeauty.fooddelivery.service.address

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.data.repo.address.IAddressRepository
import com.bunbeauty.fooddelivery.data.repo.city.ICityRepository
import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import com.bunbeauty.fooddelivery.domain.model.address.GetAddress
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import com.bunbeauty.fooddelivery.domain.model.address.PostAddress
import com.bunbeauty.fooddelivery.domain.toUuid

class AddressService(
    private val addressRepository: IAddressRepository,
    private val streetRepository: IStreetRepository,
    private val clientUserRepository: ClientUserRepository,
    private val cityRepository: ICityRepository,
) : IAddressService {

    override suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress {
        val street = streetRepository.getStreetByUuid(postAddress.streetUuid.toUuid())
            ?: error("Street with id = ${postAddress.streetUuid} was not found")
        val city = cityRepository.getCityByUuid(street.cityUuid.toUuid())
            ?: error("City with id = ${street.cityUuid} was not found")
        val clientUser = clientUserRepository.getClientUserByUuid(userUuid.toUuid())
            ?: error("User with id = $userUuid was not found")
        if (city.company.uuid != clientUser.company.uuid) {
            error("User doesn't has access to this company (${city.company.uuid})")
        }

        val insertAddress = InsertAddress(
            house = postAddress.house,
            flat = postAddress.flat,
            entrance = postAddress.entrance,
            floor = postAddress.floor,
            comment = postAddress.comment,
            streetUuid = postAddress.streetUuid.toUuid(),
            clientUserUuid = userUuid.toUuid(),
            isVisible = postAddress.isVisible,
        )

        return addressRepository.insertAddress(insertAddress)
    }

    override suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<GetAddress> =
        addressRepository.getAddressListByUserUuidAndCityUuid(userUuid.toUuid(), cityUuid.toUuid())
}