export function isLoggedIn() {
    return localStorage.getItem("customerId") !== null;
}

export function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = "index.html";
    }
}

export function logout() {
    localStorage.removeItem("customerId");
    window.location.href = "index.html";
}
