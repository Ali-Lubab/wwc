import { requireAuth } from "./auth.js";
import { apiRequest } from "./api.js";

requireAuth();

const customerId = localStorage.getItem("customerId");

async function loadBalances() {
    const balances = await apiRequest("/balances");
    const container = document.getElementById("balances");

    balances
        .filter(b => b.owner.id == customerId)
        .forEach(b => {
            container.innerHTML += `<div>${b.amount} ${b.currency}</div>`;
        });
}

async function loadTransfers() {
    const transfers = await apiRequest("/transfers");
    const container = document.getElementById("transfers");

    transfers
        .filter(t => t.sender.id == customerId)
        .forEach(t => {
            container.innerHTML += `<div>
                ${t.sourceAmount} ${t.sourceCurrency} → ${t.targetAmount} ${t.targetCurrency}
            </div>`;
        });
}

loadBalances();
loadTransfers();
