import { useState } from 'react'
import { useMutation, useQueryClient } from '@tanstack/react-query'

import {
    createRunningGoal,
    type GoalMetric,
    type RunningGoalRequest,
} from '../../lib/api'

type Props = {
    userId: number
}

const units: Record<GoalMetric, RunningGoalRequest['unit']> = {
    DISTANCE: 'km',
    DURATION: 'minutes',
    PACE: 'min/km',
}

export function RunningGoalForm({ userId }: Props) {
    const queryClient = useQueryClient()

    const [metric, setMetric] = useState<GoalMetric>('DISTANCE')
    const [target, setTartget] = useState('')
    const [start_date, setStartDate] = useState('')
    const [end_date, setEndDate] = useState('')

    // Đây là React Query hook dùng cho những thao tác thay đổi dữ liệu.
    // Giúp quản lí loading/error mà không cần tự tạo nhiều biến useState()
    const createMutation = useMutation({
        mutationFn: (goal: RunningGoalRequest) =>
            createRunningGoal(userId, goal),

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ['running-goals', userId],
            })

            setTartget('')
        },
    })

    function handleSubmit(event: React.SubmitEvent<HTMLFormElement>) {
        event.preventDefault()

        const goal: RunningGoalRequest = {
            category: 'RUNNING',
            exercise: null,
            metric,
            target: Number(target),
            unit:units[metric],
            start_date,
            end_date,
        }

        // React Query thực hiện HTTP POST
        // Đồng thời cung cấp các trạng thái: isPending, isSuccess, isError
        createMutation.mutate(goal)
    }

    return(
        <section className="card-surface p-6">
            <h2 className="text-xl font-bold mb-5">
                Create Running Goal
            </h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block mb-2">
                        Metric
                    </label>

                    <select
                        value={metric}
                        onChange={(event) =>
                            setMetric(event.target.value as GoalMetric)
                        }
                        
                        className="w-full rounded-lg border p-3">
                            <option value="DISTANCE">Distance</option>
                            <option value="DURATION">Duration</option>
                            <option value="PACE">Pace</option>
                    </select>
                </div>

                <div>
                    <label className="block mb-2">
                        Target ({units[metric]})
                    </label>

                    <input type="number"
                           min="0.01"
                           step="any"
                           required
                           value={target}
                           onChange={(event) => setTartget(event.target.value)}
                           className="w-full rounded-lg border p-3"
                           placeholder="Enter your target"
                    />
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="block mb-2">Start Date</label>

                        <input type="date"
                               required
                               value={start_date}
                               onChange={(event) =>
                                setStartDate(event.target.value)
                               }
                               className="w-full rounded-lg border p-3"
                        />
                    </div>
                </div>

                    <div>
                        <label className="block mb-2">End Date</label>

                        <input type="date"
                               required
                               min={start_date || undefined}
                               value={end_date}
                               onChange={(event) =>
                                setStartDate(event.target.value)
                               }
                               className="w-full rounded-lg border p-3"
                        />
                    </div>

                    {createMutation.isError && (
                        <p className="text-red-400">
                            {createMutation.error.message}
                        </p>
                    )}

                    <button type="submit"
                            disabled={createMutation.isPending}
                            className="rounded-lg bg-primary px-5 py-3 font-semibold text-black disabled:opacity-50"
                    >
                        {createMutation.isPending ? 'Creating...' : 'Create Goal'}
                    </button>
            </form>
        </section>
    )
}