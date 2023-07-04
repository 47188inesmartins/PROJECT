package backend.jvm.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.math.*

data class Geolocation(val latitude: Double, val longitude: Double)

@Service
class GeoCoder {
    fun getGeolocation(address: String): Geolocation? {
        val restTemplate = RestTemplate()
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=YOUR_API_KEY"
        val response = restTemplate.getForObject(url, GeocodingResponse::class.java)

        if (response?.results?.isNotEmpty() == true) {
            val location = response.results[0].geometry.location
            return Geolocation(location.lat, location.lng)
        }

        return null
    }

    fun calculateDistance(myAddress: String,companyAddress: String): Double? {
        val myGeolocation = getGeolocation(myAddress)
        val companyGeolocation = getGeolocation(companyAddress)

        if (myGeolocation != null && companyGeolocation != null) {
            return calculateHaversineDistance(myGeolocation, companyGeolocation)
        }
        return null
    }

    private fun calculateHaversineDistance(location1: Geolocation, location2: Geolocation): Double {
        val earthRadiusKm = 6371
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

data class GeocodingResponse(val results: List<GeocodingResult>)
data class GeocodingResult(val geometry: Geometry)
data class Geometry(val location: Location)
data class Location(val lat: Double, val lng: Double)
