import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/summary")({
    component: SummaryPage,
});

function SummaryPage() {
    return (
        <main>
            <h1>
                Summary
            </h1>
            <p>
                Your daily, monthly and anually analytics.
            </p>
        </main>
    );
}