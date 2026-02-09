import { requireAuth } from "./auth.js";

const API_BASE = "http://localhost:8080";

export async function apiRequest(path, method = "GET", body, requireAuthentication = true) {
    if (requireAuthentication) {
        requireAuth();
    }

    const options = {
        method,
        headers: { "Content-Type": "application/json" }
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(API_BASE + path, options);

    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Request failed");
    }

    return response.json();
}
