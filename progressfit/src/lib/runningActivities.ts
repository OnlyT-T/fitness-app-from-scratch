import type {
    RunningActivity,
    RunningActivityRequest,
} from '../types/runningActivity'

import { API_BASE_URL } from './client'

// GET
export async function getRunningActivities(
    userId: number
): Promise<RunningActivity[]> {

    const response = await fetch(`${API_BASE_URL}/users/${userId}/running-activities`)

    if (!response.ok) {
        throw new Error('Failed to fetch running activities')
    }

    return response.json()
}

// POST
export async function createRunningActivity(
    userId: number,
    data: RunningActivityRequest
): Promise<RunningActivity> {

    const response = await fetch(`${API_BASE_URL}/users/${userId}/running-activities`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        }
    )

    if (!response.ok) {
        throw new Error('Failed to create a running activity')
    }

    return response.json()
}

// DELETE
export async function deleteRunningActivity(
    userId: number,
    activityId: number
): Promise<void> {

    const response = await fetch(`${API_BASE_URL}/users/${userId}/running-activities/${activityId}`,
        {
            method: 'DELETE',
        }
    )

    if (!response.ok) {
        throw new Error('Failed to delete a running activity')
    }
}