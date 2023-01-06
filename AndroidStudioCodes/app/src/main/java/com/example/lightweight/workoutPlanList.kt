package com.example.lightweight

class workoutPlanList{
    var workoutid: String? = null
    var inside: ArrayList<workoutplan>? = ArrayList<workoutplan>()

    constructor() {}
    constructor(workoutid: String?, value_workout: ArrayList<workoutplan>?) {
        this.workoutid = workoutid
        this.inside = value_workout
    }


}

