import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/auth")({
    component: AuthPage,
});

function AuthPage() {
    return (
        <main>
            <h1>
                Login/Register
            </h1>
            <p>
                Authentication will be implemented later.
            </p>
        </main>
    );
}