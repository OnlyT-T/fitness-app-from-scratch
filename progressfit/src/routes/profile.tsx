import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/profile")({
    component: ProfilePage,
});

function ProfilePage() {
    return (
        <main>
            <h1>
                Profile
            </h1>
            <p>
                User profile and settings.
            </p>
        </main>
    );
}