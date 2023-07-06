package backend.jvm.utils

import backend.jvm.utils.errorHandling.InvalidAddress

object AddressUtils {
    fun getCoordinatesFromAddress(street:String,city:String,country:String): Geolocation {
        val getAddress = AddressInformation(street,city,country)
        return GeoCoder().getGeolocation(getAddress)?:throw InvalidAddress()
    }

    fun addressInfo(street:String,city:String,country:String) = "$street,$city,$country"
}