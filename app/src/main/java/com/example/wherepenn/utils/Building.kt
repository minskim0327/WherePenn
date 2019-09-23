package com.example.wherepenn.utils


// Data Class For Buildings
data class Building(
    var additional_text : String = "",
    var address: String = "",
    var aka: String = "",
    var art_type: String = "",
    var campus_item_building_type: ArrayList<CampusBuilding>? = null,
    var campus_item_images: ArrayList<CampusImage>? = null,
    var campus_item_type_id: Long = 0,
    var city: String = "",
    var content_type: String = "",
    var description: String = "",
    var emergency_address: String = "",
    var facilities_id: Int = 0,
    var floors: String = "",
    var gross_area: String = "",
    var http_link: String = "",
    var id: Long = 0,
    var keywords: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var pdf_link: String = "",
    var state: String = "",
    var title: String = "",
    var year_built: String = "",
    var zip_code: String = "") {
    data class CampusBuilding(
        var id: Long = 0,
        var label: String = "",
        var school: Boolean = true
    )
    data class CampusImage(
        var id: Long = 0,
        var image_url: String = "",
        var thumbnail_url: String = "",
        var title: String = ""
    )
}