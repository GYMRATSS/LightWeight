package com.example.lightweight

class workoutplan {

    /*var planid: String? = null*/
    var id: String? = null
    var ağırlık: String? = null
    var set: String? = null
    var tekrar: String? = null

    constructor() {}
    constructor(workoutid: String?, value_workout: ArrayList<String>) {
        /*this.planid = planid*/
        this.id = workoutid
        this.ağırlık = value_workout[0]
        this.set = value_workout[2]
        this.tekrar = value_workout[3]


    }

    constructor(workoutid: String?, ağırlık: String?, set: String?, tekrar: String?) {
        /*this.planid = planid*/
        this.id = workoutid
        this.ağırlık = ağırlık
        this.set = set
        this.tekrar = tekrar
    }
}