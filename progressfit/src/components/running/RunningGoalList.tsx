import {
    useMutation,
    useQuery,
    useQueryClient,
} from '@tanstack/react-query'

import {
    deleteRunningGoal,
    getRunningGoals,
} from '../../lib/api'

type Props = {
    userId: number
}

export function RunningGoalList({ userId }: Props)
{
    // queryClient dùng để quản lí cache
    const queryClient = useQueryClient()

    const goalsQuery = useQuery({
        queryKey: ['running-goals', userId],
        queryFn: () => getRunningGoals(userId)
    })

    const deleteMutation = useMutation({
        mutationFn: (goalId:number) =>
            deleteRunningGoal(userId, goalId),

        onSuccess: () => {
            // Sau khi tạo hoặc xóa Goal, React Query đánh
            // dấu dữ liệu Goals trong cache là cũ và thường
            // sẽ tự fetch lại query đang được sử dụng.
            // Từ đó, giao diện cập nhật mà không cần reload lại trang.
            queryClient.invalidateQueries({
                queryKey: ['running-goals', userId],
            })
        }
    })

    if (goalsQuery.isPending) {
        return <p>Loading goals...</p>
    }

    if (goalsQuery.isError) {
        return (
            <p className="text-red-400">
                {goalsQuery.error.message}
            </p>
        )
    }

    return (
        <section className="mt-8">
            <h2 className="text-xl font-bold mb-5">
                Your Running Goals
            </h2>

            {goalsQuery.data.length === 0 ? (
                <p>No running goals yet.</p>
            ) : (
                <div className="space-y-4">
                    {goalsQuery.data.map((goal) => (
                        <article key={goal.id}
                                 className="card-surface p-5"
                        >
                            <h3 className="font-bold text-lg">
                                {goal.metric}: {goal.target} {goal.unit}
                            </h3>

                            <p className="text-sm opacity-70 mt-2">
                                {goal.start_date} &rarr; {goal.end_date}
                            </p>

                            <button type="button"
                                    disabled={deleteMutation.isPending}
                                    onClick={() =>
                                        deleteMutation.mutate(goal.id)
                                    }
                                    className="mt-4 rounded-lg border px-4 py-2"
                            >
                                Delete
                            </button>
                        </article>
                    ))}
                </div>
            )}

            {deleteMutation.isError && (
                <p className="mt-3 text-red-400">
                    {deleteMutation.error.message}
                </p>
            )}
        </section>
    )
}