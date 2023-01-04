package com.example.lightweight

class meal {
    var id: String? = null
    var kalori: String? = null
    var karbonhidrat: String? = null
    var protein: String? = null
    var yağ: String? = null

    constructor() {}
    constructor(id: String?, value: ArrayList<String>) {
        this.id = id
        this.kalori = value[0]
        this.karbonhidrat = value[1]
        this.protein = value[2]
        this.yağ = value[3]

    }
    constructor(id: String?, kalori: String?, karbonhidrat: String?, protein: String?, yağ: String?) {
        this.id = id
        this.kalori = kalori
        this.karbonhidrat = karbonhidrat
        this.protein = protein
        this.yağ = yağ
    }

}