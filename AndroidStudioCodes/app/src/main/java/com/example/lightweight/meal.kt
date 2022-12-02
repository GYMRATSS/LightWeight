package com.example.lightweight

class meal {
    var id: String? = null
    var kalori: String? = null
    var karbonhidrat: String? = null
    var protein: String? = null
    var yağ: String? = null

    constructor() {}
    constructor(kalori: String?, karbonhidrat: String?, protein: String?, yağ: String?) {
        this.kalori = kalori
        this.karbonhidrat = karbonhidrat
        this.protein = protein
        this.yağ = yağ
    }
}