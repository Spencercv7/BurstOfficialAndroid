package betalab.ca.burstofficialandroid.data.network.response

import betalab.ca.burstofficialandroid.data.db.entity.*
import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("events")
    val events: Array<String>,
    @SerializedName("following")
    val following: Array<String>,
    @SerializedName("interest")
    val interest: Array<String>,
    @SerializedName("isOrganization")
    val isOrganization: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("program")
    val program: String,
    @SerializedName("schedule")
    val schedule: String,
    @SerializedName("school")
    val school: String
) {
    fun toEntry(): PeopleEntry {
        return PeopleEntry(
            avatar,
            email,
            events,
            following,
            interest,
            isOrganization,
            name,
            program,
            schedule,
            school

        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PeopleResponse

        if (avatar != other.avatar) return false
        if (email != other.email) return false
        if (!events.contentEquals(other.events)) return false
        if (!following.contentEquals(other.following)) return false
        if (!interest.contentEquals(other.interest)) return false
        if (isOrganization != other.isOrganization) return false
        if (name != other.name) return false
        if (program != other.program) return false
        if (schedule != other.schedule) return false
        if (school != other.school) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + events.hashCode()
        result = 31 * result + following.hashCode()
        result = 31 * result + interest.contentHashCode()
        result = 31 * result + isOrganization.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + program.hashCode()
        result = 31 * result + schedule.hashCode()
        result = 31 * result + school.hashCode()
        return result
    }
}