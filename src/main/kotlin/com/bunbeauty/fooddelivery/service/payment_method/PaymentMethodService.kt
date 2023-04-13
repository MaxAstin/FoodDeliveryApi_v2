package com.bunbeauty.fooddelivery.service.payment_method

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.payment_method.GetPaymentMethod
import com.bunbeauty.fooddelivery.data.repo.payment_method.PaymentMethodRepository

class PaymentMethodService(
    private val paymentMethodRepository: PaymentMethodRepository
): IPaymentMethodService {

    override suspend fun getPaymentMethodByCompanyUuid(companyUuid: String): List<GetPaymentMethod> {
        return paymentMethodRepository.getPaymentMethodListByCompanyUuid(companyUuid.toUuid())
    }

}