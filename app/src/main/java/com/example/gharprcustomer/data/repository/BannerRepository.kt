package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.BannerModel

class BannerRepository {
    // Function to fetch banners
    suspend fun getBanners(): List<BannerModel> {
        return listOf(
            BannerModel(
                1,
                "Summer Sale",
                "Huge discounts on all items!",
                "image_url",
                "fallback_image_url",
                true,
                "2024-12-01",
                "2024-12-31",
                "2024-11-25",
                "2024-11-25"
            ),
            BannerModel(
                2,
                "Winter Clearance",
                "Up to 50% off on winter collection!",
                "winter_sale_image_url",
                "fallback_winter_image_url",
                true,
                "2024-12-15",
                "2025-01-15",
                "2024-12-01",
                "2024-12-01"
            ),
            BannerModel(
                3,
                "Winter Clearance",
                "Up to 50% off on winter collection!",
                "winter_sale_image_url",
                "fallback_winter_image_url",
                true,
                "2024-12-15",
                "2025-01-15",
                "2024-12-01",
                "2024-12-01"
            ),
            BannerModel(
                4,
                "Winter Clearance",
                "Up to 50% off on winter collection!",
                "winter_sale_image_url",
                "fallback_winter_image_url",
                true,
                "2024-12-15",
                "2025-01-15",
                "2024-12-01",
                "2024-12-01"
            ),
            BannerModel(
                5,
                "Winter Clearance",
                "Up to 50% off on winter collection!",
                "winter_sale_image_url",
                "fallback_winter_image_url",
                true,
                "2024-12-15",
                "2025-01-15",
                "2024-12-01",
                "2024-12-01"
            ),
            BannerModel(
                6,
                "Winter Clearance",
                "Up to 50% off on winter collection!",
                "winter_sale_image_url",
                "fallback_winter_image_url",
                true,
                "2024-12-15",
                "2025-01-15",
                "2024-12-01",
                "2024-12-01"
            )
        )
    }
}