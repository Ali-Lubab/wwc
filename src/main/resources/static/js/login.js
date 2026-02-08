import { apiRequest } from "./api.js";

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("loginForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        try {
            const customer = await apiRequest("/auth/login", "POST", {
                email: document.getElementById("email").value,
                password: document.getElementById("password").value
            });
            localStorage.setItem("customerId", customer.id);
            window.location.href = "home.html";
        } catch (error) {
            alert(error.message || "Login failed");
        }
    });
});
