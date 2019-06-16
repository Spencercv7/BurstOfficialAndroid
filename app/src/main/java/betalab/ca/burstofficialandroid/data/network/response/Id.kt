package betalab.ca.burstofficialandroid.data.network.response


import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("\$oid")
    val oid: String
)