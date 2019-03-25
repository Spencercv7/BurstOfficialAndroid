package betalab.ca.burstandroid

import java.io.Serializable

data class CardOBJ(
    var label: String? = null,
    var description: String? = null,
    var date: String? = null,
    var time: String? = null,
    var location: String? = null)