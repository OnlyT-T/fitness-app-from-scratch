export type GoalMetric = 'DISTANCE' | 'DURATION' | 'PACE'

export type RunningGoalRequest = {
    category: 'RUNNING'
    exercise: null
    metric: GoalMetric
    target: number
    unit: 'km' | 'minutes' | 'min/km'
    start_date: string
    end_date: string
}

export type RunningGoal = RunningGoalRequest & {
    id: number
    userId: string
    isActive: boolean
}