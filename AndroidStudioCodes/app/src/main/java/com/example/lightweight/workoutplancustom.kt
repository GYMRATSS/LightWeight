package com.example.lightweight

class workoutplancustom {
    var id: String? = null
    var ağırlık: String? = null
    var set: String? = null
    var tekrar: String? = null

    constructor() {}
    constructor(workoutid: String?, value_workout: ArrayList<String>) {
        this.id = workoutid
        this.ağırlık = value_workout[0]
        this.set = value_workout[1]
        this.tekrar = value_workout[2]

    }
    constructor(workoutid: String?, ağırlık: String?, set: String?, tekrar: String?) {
        this.id = workoutid
        this.ağırlık = ağırlık
        this.set = set
        this.tekrar = tekrar
    }


}