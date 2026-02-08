export function isLoggedIn() {
    return localStorage.getItem("customerId") !== null;
}

export function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = "index.html";
    }
}

async function login(event) {
    event.preventDefault();

    try {
        const customer = await apiRequest("/auth/login", "POST", {
            email: email.value,
            password: password.value
        });

        localStorage.setItem("customerId", customer.id);
        window.location.href = "home.html";
    } catch (e) {
        alert("Invalid email or password");
    }
}

export function logout() {
    localStorage.removeItem("customerId");
    window.location.href = "index.html";
}
