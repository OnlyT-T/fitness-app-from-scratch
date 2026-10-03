const API_BASE_URL = 'http://localhost:8082/api'

export async function checkBackendHealth() {
    const response = await fetch(`${API_BASE_URL}/health`)

    if (!response.ok) {
        throw new Error('Backend is unavailable')
    }
    
    return response.text()
}