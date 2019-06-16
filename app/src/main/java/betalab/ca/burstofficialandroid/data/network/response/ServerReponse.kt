package betalab.ca.burstofficialandroid.data.network.response


import com.google.gson.annotations.SerializedName

data class ServerReponse(
    @SerializedName("success")
    val success: Boolean
)