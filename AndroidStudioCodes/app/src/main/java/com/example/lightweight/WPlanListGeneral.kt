package com.example.lightweight


////nothing done yet maybe for general display of workouts
class WPlanListGeneral {
    var workoutid: String? = null
    var name: String? = null

    constructor() {}
    constructor(workoutid: String?, value_workout: String?) {
        this.workoutid = workoutid
        this.name = value_workout
    }
}