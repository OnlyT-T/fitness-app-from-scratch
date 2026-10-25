const API_BASE_URL = 'http://localhost:8082/api'

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

async function handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
        const message = await response.text()

        throw new Error(
            `HTTP ${response.status}: ${message}`
        )
    }

    return response.json() as Promise<T>
}

export async function getRunningGoals(
    userId: number
): Promise<RunningGoal[]> {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/goals`)

    return handleResponse<RunningGoal[]>(response);
}

export async function createRunningGoal(
    userId: number,
    goal: RunningGoalRequest
): Promise<RunningGoal> {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/goals`,
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(goal),
    })

    return handleResponse<RunningGoal>(response);
}

export async function deleteRunningGoal(
    userId: number,
    goalId: number
): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/goals/${goalId}`,
        {
            method: 'DELETE',
        }
    )

    if (!response.ok) {
        throw new Error(
            `Failed to delete goal: HTTP ${response.status}`
        )
    }
}

export async function checkBackendHealth() {
    const response = await fetch(`${API_BASE_URL}/health`)

    if (!response.ok) {
        throw new Error('Backend is unavailable')
    }
    
    return response.text()
}

