import { API_BASE_URL } from "./client"

export async function checkBackendHealth() {
    const response = await fetch(`${API_BASE_URL}/health`)

    if (!response.ok) {
        throw new Error('Backend is unavailable')
    }
    
    return response.text()
}