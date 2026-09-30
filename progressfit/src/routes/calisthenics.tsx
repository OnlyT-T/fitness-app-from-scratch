import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/calisthenics")({
    component: CalisthenicsPage,
});

function CalisthenicsPage() {
    return (
        <main>
            <h1>
                Calisthenics
            </h1>
            <p>
                Track push-ups, pull-ups, squats and more.
            </p>
        </main>
    );
}