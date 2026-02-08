import { requireAuth } from "./auth.js";

requireAuth();

const customerId = localStorage.getItem("customerId");

async function loadRecipients() {
    const recipients = await apiRequest("/recipients");
    const container = document.getElementById("recipients");

    recipients
        .filter(r => r.owner.id == customerId)
        .forEach(r => {
            container.innerHTML += `<div>${r.firstName} ${r.lastName}</div>`;
        });
}

loadRecipients();
