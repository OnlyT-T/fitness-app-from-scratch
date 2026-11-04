import { createFileRoute } from "@tanstack/react-router";

// import { RunningGoalForm } from '../components/running/RunningGoalForm'
// import { RunningGoalList } from '../components/running/RunningGoalList'
import { RunningActivityForm } from '../components/running/RunningActivityForm'
import { RunningActivityHistory } from '../components/running/RunningActivityHistory'

export const Route = createFileRoute("/running")({
    component: RunningPage,
});

function RunningPage() {
    // Sử dụng user ID tạm thời cho local development
    // Thay bằng authenticated user sau.
    const userId = 2

    return (
        <main className="mx-auto max-w-4xl space-y-8 p-6">

            <header className="mb-8">
                <h1 className="text-3xl font-bold">
                    Running Dashboard
                </h1>

                <p className="text-gray-500">
                    Track your running performance.
                </p>
            </header>

            <RunningActivityForm userId={userId} />

            <RunningActivityHistory userId={userId} />

        </main>
    );
}