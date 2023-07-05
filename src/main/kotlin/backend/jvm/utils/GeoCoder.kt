package backend.jvm.utils

import org.springframework.web.client.RestTemplate
import kotlin.math.*

data class Geolocation(val latitude: Double, val longitude: Double)
data class AddressInformation(val street: String, val city: String, val country: String)
data class GeocodingResponse(val results: List<GeocodingResult>)
data class GeocodingResult(val geometry: Geometry)
data class Geometry(val location: Location)
data class Location(val lat: Double, val lng: Double)


class GeoCoder {
    companion object {
        const val earthRadiusKm: Double = 6372.8
        const val urlGeoApi = "https://maps.googleapis.com/maps/api/geocode/json?address="
    }

    /**
     * get the coordinates of a specific address
     * @param address contains the address information
     * @return the geolocalization of the address
     */
    fun getGeolocation(address: AddressInformation): Geolocation? {
        val restTemplate = RestTemplate()
        val addressWithoutSpaces = "${address.street.trim()},${address.city.trim()},${address.country.trim()}"
        val url = "$urlGeoApi$addressWithoutSpaces&key=AIzaSyAGEDxzvzIYUuKM5PzGhlcSuU-VVnii2hU"
        val response = restTemplate.getForObject(url, GeocodingResponse::class.java)

        if (response?.results?.isNotEmpty() == true) {
            val location = response.results[0].geometry.location
            return Geolocation(location.lat, location.lng)
        }
        return null
    }

    /**
     * Calculates the distance between two addresses
     * @param myAddress is the user address
     * @param companyAddress is the company address
     * @return Distance in kms
     */
    fun calculateDistance(myAddress: AddressInformation, companyAddress: AddressInformation): Double? {
        val myGeolocation = getGeolocation(myAddress)
        val companyGeolocation = getGeolocation(companyAddress)

        if (myGeolocation != null && companyGeolocation != null) {
            return calculateHaversineDistance(myGeolocation, companyGeolocation)
        }
        return null
    }

    /**
     * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
     * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
     * sides and angles of spherical "triangles".
     *
     *
     * @return Distance in kilometers
     */
    fun calculateHaversineDistance(location1: Geolocation, location2: Geolocation): Double {
        val lat1 = Math.toRadians(location1.latitude)
        val lon1 = Math.toRadians(location1.longitude)
        val lat2 = Math.toRadians(location2.latitude)
        val lon2 = Math.toRadians(location2.longitude)

        val dLon = lon2 - lon1
        val dLat = lat2 - lat1

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(lat1) * cos(lat2) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadiusKm * c
    }
}

