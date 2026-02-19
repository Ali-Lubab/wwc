import { apiRequest } from "./api.js";

async function loadRecipients() {
    const tbody = document.querySelector("#recipients tbody");
    const tableContainer = document.querySelector(".table-container");

    try {
        const recipients = await apiRequest("/recipients");

        if (recipients.length === 0) {
            tableContainer.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">👥</div>
                    <p>No recipients yet</p>
                    <p style="font-size: 0.875rem;">Add your first recipient to start sending transfers</p>
                </div>
            `;
            return;
        }

        tbody.innerHTML = '';

        recipients.forEach(r => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${r.id}</td>
                <td style="font-weight: 500;">${r.firstName} ${r.lastName}</td>
                <td>${r.email || '-'}</td>
                <td>${r.accountNumber}</td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        tableContainer.innerHTML = `
            <div class="error-message">
                ${error.message}
            </div>
        `;
    }
}

loadRecipients();
