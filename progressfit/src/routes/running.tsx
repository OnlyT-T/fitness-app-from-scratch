import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/running")({
    component: RunningPage,
});

function RunningPage() {
    return (
        <main>
            <h1>
                Running
            </h1>
            <p>
                Running goals and workout tracking will be go here.
            </p>
        </main>
    );
}