import { apiRequest } from "./api.js";

async function loadRecipients() {
    const recipients = await apiRequest("/recipients");
    const tbody = document.querySelector("#recipients tbody");

    recipients.forEach(r => {
        tbody.innerHTML += `<tr>
            <td>${r.id}</td>
            <td>${r.firstName} ${r.lastName}</td>
            <td>${r.currency}</td>
            <td>${r.accountNumber}</td>
        </tr>`;
    });
}

loadRecipients();
