package com.bunbeauty.fooddelivery.service.menu_product

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.menu_product.*
import com.bunbeauty.fooddelivery.data.repo.category.ICategoryRepository
import com.bunbeauty.fooddelivery.data.repo.hit.IHitRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository

class MenuProductService(
    private val menuProductRepository: IMenuProductRepository,
    private val userRepository: IUserRepository,
    private val categoryRepository: ICategoryRepository,
    private val hitRepository: IHitRepository,
) : IMenuProductService {

    override suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct? {
        val companyUuid = userRepository.getCompanyUuidByUserUuid(creatorUuid.toUuid()) ?: return null
        val insertMenuProduct = InsertMenuProduct(
            name = postMenuProduct.name,
            newPrice = postMenuProduct.newPrice,
            oldPrice = postMenuProduct.oldPrice,
            utils = postMenuProduct.utils,
            nutrition = postMenuProduct.nutrition,
            description = postMenuProduct.description,
            comboDescription = postMenuProduct.comboDescription,
            photoLink = postMenuProduct.photoLink,
            barcode = postMenuProduct.barcode,
            companyUuid = companyUuid.toUuid(),
            categoryUuids = postMenuProduct.categoryUuids.map { categoryUuid ->
                categoryUuid.toUuid()
            },
            isVisible = postMenuProduct.isVisible,
        )

        return menuProductRepository.insertMenuProduct(insertMenuProduct)
    }

    override suspend fun updateMenuProduct(
        menuProductUuid: String,
        patchMenuProduct: PatchMenuProduct,
    ): GetMenuProduct? {
        val updateMenuProduct = UpdateMenuProduct(
            name = patchMenuProduct.name,
            newPrice = patchMenuProduct.newPrice,
            oldPrice = patchMenuProduct.oldPrice,
            utils = patchMenuProduct.utils,
            nutrition = patchMenuProduct.nutrition,
            description = patchMenuProduct.description,
            comboDescription = patchMenuProduct.comboDescription,
            photoLink = patchMenuProduct.photoLink,
            barcode = patchMenuProduct.barcode,
            categoryUuids = patchMenuProduct.categoryUuids?.map { uuid ->
                uuid.toUuid()
            },
            isVisible = patchMenuProduct.isVisible,
        )
        return menuProductRepository.updateMenuProduct(menuProductUuid.toUuid(), updateMenuProduct)
    }

    override suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct> {
        val menuProductList =
            menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid()).filter { menuProduct ->
                menuProduct.isVisible
            }

        val hits = hitRepository.getHitsByCompanyUuid(companyUuid)
        if (hits.isNotEmpty()) {
            val hitsCategory = categoryRepository.getHitsCategory()
            menuProductList.forEach { menuProduct ->
                if (hits.contains(menuProduct.uuid)) {
                    menuProduct.categories.add(hitsCategory)
                }
            }
        }

        return menuProductList
    }
}