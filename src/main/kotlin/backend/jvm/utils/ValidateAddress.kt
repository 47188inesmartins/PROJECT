package backend.jvm.utils
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.AddressComponentType
import com.google.maps.model.GeocodingResult


object ValidateAddress {

    fun checkValidAddressWithCoordinates(address: String): String? {
        val context = GeoApiContext.Builder()
            .apiKey("")
            .build()

        val result = GeocodingApi.geocode(context, address).await()
        if (result.isNotEmpty()) {
            val addressComponents = result[0].addressComponents
            for (component in addressComponents) {
                val types = component.types
                for (type in types) {
                    if (type == AddressComponentType.LOCALITY) {
                        return component.longName
                    }
                }
            }
        }
        return null // se nao retornar nada é porque o endereço nao existe
    }

}