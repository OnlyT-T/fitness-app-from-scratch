export type RunningActivity = {
    id: number
    userId: number
    performedAt: string
    distanceKm: number
    durationSec: number
    calories: number | null
    avgPace: number
    avgSpeed: number
    notes: string | null
}

export type RunningActivityRequest = {
    performedAt: string
    distanceKm: number
    durationSec: number
    calories?: number | null
    notes?: string
} 