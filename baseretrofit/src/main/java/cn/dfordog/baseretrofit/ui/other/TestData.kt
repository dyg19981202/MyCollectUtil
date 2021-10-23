package cn.dfordog.baseretrofit.ui.other

data class TestData(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val address: String,
    val attention: String,
    val id: Int,
    val img: List<String>,
    val is_col: Int,
    val lat: Any,
    val lng: Any,
    val store_name: String,
    val venues: String
)