import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'

import {
    getRunningActivities,
    deleteRunningActivity,
} from '../../lib/runningActivities'

type Props = {
    userId: number
}

export function RunningActivityHistory({ userId }: Props) {

    const queryClient = useQueryClient()

    const {
        data: activities = [],
        isLoading,
        isError,
    } = useQuery({
        queryKey: ['runningActivities', userId],
        queryFn: () => getRunningActivities(userId),
    })

    const deleteMutation = useMutation({
       
        mutationFn: (activityId: number) =>
            deleteRunningActivity(userId, activityId),

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ['runningActivities', userId]
            })
        },
    })

    if (isLoading) {
        return <p>Loading running activities...</p>
    }

    if (isError) {
        return <p>Failed to load running activities.</p>
    }

    return (
        <section className="space-y-4">

            <h2 className="text-xl font-semibold">
                Running History
            </h2>

            {activities.length === 0 && (
                <p>No running activities yet.</p>
            )}

            {activities.map((activity) => (

                <div
                    key={activity.id}
                    className="rounded-lg border p-4 shadow-sm"
                >

                    <h3 className="font-semibold">
                        {activity.performedAt}
                    </h3>

                    <p>
                        Distance: {activity.distanceKm}
                    </p>

                    <p>
                        Duration: {
                            (activity.durationSec / 60).toFixed(1)
                        } minutes
                    </p>

                    <p>
                        Average Pace: {activity.avgPace} min/km
                    </p>

                    <p>
                        Average Speed: {activity.avgSpeed} km/h
                    </p>

                    {activity.calories !== null && (
                        <p> 
                            Calories: {activity.calories} kcal
                        </p>
                    )}

                    {activity.notes && (
                        <p>
                            Notes: {activity.notes}
                        </p>
                    )}

                    <button
                        onClick={() =>
                            deleteMutation.mutate(activity.id)
                        }
                        disabled={deleteMutation.isPending}
                        className="mt-3 rounded bg-red-500 px-3 py-1 text-white hover:bg-red-600"
                    >
                        Delete
                    </button>

                </div>

            ))}

            {deleteMutation.isError && (
                <p className="text-red-500">
                    Failed to delete activity.
                </p>
            )}
            
        </section>
    )
}