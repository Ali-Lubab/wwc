import { requireAuth } from "./auth.js";
import { apiRequest } from "./api.js";

requireAuth();

const customerId = localStorage.getItem("customerId");

async function loadRecipients() {
    const recipients = await apiRequest("/recipients");
    const tbody = document.querySelector("#recipients tbody");

    recipients
        .filter(r => r.ownerId == customerId)
        .forEach(r => {
            tbody.innerHTML += `<tr>
                <td>${r.id}</td>
                <td>${r.firstName} ${r.lastName}</td>
                <td>${r.currency}</td>
                <td>${r.accountNumber}</td>
            </tr>`;
        });
}

loadRecipients();
