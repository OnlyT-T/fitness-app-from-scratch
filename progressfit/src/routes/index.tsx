import { useState } from 'react'
import { createFileRoute } from '@tanstack/react-router'
import { checkBackendHealth } from '../lib/api'

export const Route = createFileRoute('/')({ component: App })

function App() {
  const [backendStatus, setBackendStatus] = useState('Not checked')

  async function handleCheckBackend() {
    try {
      const message = await checkBackendHealth()
      setBackendStatus(message)
    } catch (error) {
      console.error(error)
      setBackendStatus('Cannot connect to backend.')
    }
  }

  return (
    <main className="min-h-screen p-8">
      <h1 className="text-gradient-energy inline-block pb-1 text-6xl font-bold leading-tight">
        ProgressFit
      </h1>

      <p className="mt-4 text-muted-foreground">
        Track your fitness. Build your progress!
      </p>

      <div className="mt-8 grid gap-4 md:grid-cols-3">
        {/* RUNNING */}
        <div className="card-surface">
          <h2 className="text-xl font-semibold text-primary">
            Running
          </h2>

          <p className="mt-2 color-primary-foreground">
            Track distance, duration and pace.
          </p>

          <button className="mt-4 rounded-lg bg-primary hover:bg-primary-hover active:bg-primary-active px-4 py-2 font-medium text-primary-foreground">
            Start Running
          </button>
        </div>

        {/* CALISTHENICS */}
        <div className="card-surface">
          <h2 className="text-xl font-semibold text-accent">
            Calisthenics
          </h2>

          <p className="mt-2 text-accent-foreground">
            Track your reps and exercises.
          </p>

          <button className="mt-4 rounded-lg bg-accent hover:bg-accent-hover active:bg-accent-active px-4 py-2 font-medium text-accent-foreground">
            Start Workour
          </button>
        </div>

        {/* CHECK BACKEND */}
        <div className="card-surface">
          <h2 className="text-xl font-semibold">
            Backend Connection
          </h2>

          <p className="mt-2 color-primary-foreground">
            {backendStatus}
          </p>

          <button
            onClick={handleCheckBackend}
            className="mt-4 rounded-lg bg-primary hover:bg-primary-hover active:bg-primary-active px-4 py-2 font-medium text-primary-foreground"
          >
            Check Backend
          </button>
        </div>
      </div>
    </main>
  )
}
