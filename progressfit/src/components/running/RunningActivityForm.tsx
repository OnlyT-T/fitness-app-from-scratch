import { useState } from 'react'
import { useMutation, useQueryClient } from '@tanstack/react-query'

import { createRunningActivity } from '../../lib/runningActivities'
import type { RunningActivityRequest } from '../../types/runningActivity'

type Props = {
    userId: number
}

export function RunningActivityForm({ userId }: Props) {
    
    const queryClient = useQueryClient()

    const [performedAt, setPerformedAt] = useState(new Date().toLocaleDateString('en-CA'))

    const [distanceKm, setDistanceKm] = useState('')
    const [durationMin, setDurationMin] = useState('')
    const [calories, setCalories] = useState('')
    const [notes, setNotes] = useState('')

    const mutation = useMutation({
        mutationFn: (variables: {
            userId: number
            data: RunningActivityRequest
        }) => createRunningActivity(
            variables.userId,
            variables.data
        ),
        onSuccess: () => {
            
            // Update the history
            queryClient.invalidateQueries({
                queryKey: ['runningActivities', userId]
            })

            // Set input back to empty
            setDistanceKm('')
            setDurationMin('')
            setCalories('')
            setNotes('')
        },
    })

    /*
    Click SAVE button flow:
        handleSubmit() -> mutation.mutate() -> createRunningActivity() -> Spring Boot API -> PostgreSQL -> onSuccess() -> invalidateQueries()
    */
    function handleSubmit(event: React.SubmitEvent<HTMLFormElement>) {

        event.preventDefault()

        const distance = Number(distanceKm)
        const minutes = Number(durationMin)

        if (!Number.isFinite(distance) || distance < 0.01 ||
            !Number.isFinite(minutes) || minutes <= 0) {
            console.error('Invalid distance or duration')
            return
        }

        if (calories !== '' && (!Number.isInteger(Number(calories)) ||
            Number(calories) < 0)
        ) {
            console.error('Invalid calories')
            return
        }

        mutation.mutate({
            userId,
            data: {
                performedAt,
                distanceKm: distance,
                durationSec: Math.round(minutes * 60),
                calories: calories === '' ? null : Number(calories),
                notes,
            },
        })
    }

    return (
        <form
            onSubmit={handleSubmit}
            onInvalidCapture={(event) => {
                const input = event.target as HTMLInputElement

                console.log('Invalid input: ', {
                    field: input.type,
                    value: input.value,
                    message: input.validationMessage,
                })
            }}
            className="space-y-4"
        >

            <h2 className="text-xl font-semibold">
                Add Running Activity
            </h2>

            <div>
                <label>Date</label>

                <input
                    type="date"
                    value={performedAt}
                    onChange={(e) => setPerformedAt(e.target.value)}
                    required
                    className="w-full rounded border p-2"
                />
            </div>

            <div>
                <label>
                    Distance (km)
                </label>

                <input
                    type="number"
                    min="0.01"
                    step="0.01"
                    value={distanceKm}
                    onChange={(e) => setDistanceKm(e.target.value)}
                    required
                    className="w-full rounded border p-2"    
                />
            </div>

            <div>
                <label>
                    Duration (minutes)
                </label>

                <input
                    type="number"
                    min="0.0167"
                    step="any"
                    value={durationMin}
                    onChange={(e) => setDurationMin(e.target.value)}
                    required
                    className="w-full rounded border p-2"    
                />
            </div>

            <div>
                <label>
                    Calories (optional)
                </label>

                <input
                    type="number"
                    min="0"
                    value={calories}
                    onChange={(e) => setCalories(e.target.value)}
                    className="w-full rounded border p-2"
                />
            </div>

            <div>
                <label>
                    Note (optional)
                </label>

                <input
                    value={notes}
                    onChange={(e) => setNotes(e.target.value)}
                    className="w-full rounded border p-2"    
                />
            </div>

            <button
                type="submit"
                disabled={mutation.isPending}
                className="mt-4 rounded-lg bg-primary hover:bg-primary-hover active:bg-primary-active px-4 py-2 font-medium text-primary-foreground"
            >
                {mutation.isPending ? 'Saving...' : 'Save Activity'}
            </button>

            {mutation.isError && (
                <p className="text-red-500">
                    Failed to save activity.
                </p>
            )}

        </form>
    )
}