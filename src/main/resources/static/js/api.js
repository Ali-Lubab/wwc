const API_BASE = "http://localhost:8080";

export async function apiRequest(path, method = "GET", body) {
    const options = {
        method,
        headers: { "Content-Type": "application/json" }
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(API_BASE + path, options);

    if (!response.ok) {
        const json = await response.json();
        throw new Error(json.error || "Request failed");
    }

    return response.json();
}
