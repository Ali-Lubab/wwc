import { logout } from "./auth.js";

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("logoutForm")?.addEventListener("submit", (e) => {
        e.preventDefault();
        logout();
    });
});
