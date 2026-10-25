import { createFileRoute } from "@tanstack/react-router";

import { RunningGoalForm } from '../components/running/RunningGoalForm'
import { RunningGoalList } from '../components/running/RunningGoalList'

export const Route = createFileRoute("/running")({
    component: RunningPage,
});

function RunningPage() {
    // Sử dụng user ID tạm thời cho local development
    // Thay bằng authenticated user sau.
    const userId = 2
    return (
        <main className="mx-auto max-w-5xl px-6 py-10">
            <header className="mb-8">
                <h1 className="text-4xl font-bold">
                    Running
                </h1>

                <p className="mt-3 opacity-70">
                    Set your running goals and track your progress.
                </p>
            </header>

            <RunningGoalForm userId={userId} />

            <RunningGoalList userId={userId} />
        </main>
    );
}