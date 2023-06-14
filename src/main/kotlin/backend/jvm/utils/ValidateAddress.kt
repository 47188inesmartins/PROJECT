package backend.jvm.utils
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult


object ValidateAddress {

    fun checkValidAddressWithCoordinates(address: String): GeocodingResult? {
        val context = GeoApiContext.Builder()
            .apiKey("")
            .build()

        val resultCoordinates = GeocodingApi.geocode(context, address).await()
        if (resultCoordinates.isNotEmpty()) {
            return resultCoordinates[0]
        }
        return null // se nao retornar nada é porque o endereço nao existe
    }

}