import { apiRequest } from "./api.js";

async function loadBalances() {
    const balances = await apiRequest("/balances");
    const container = document.getElementById("balances");

    balances.forEach(b => {
        container.innerHTML += `<div>${b.amount} ${b.currency}</div>`;
    });
}

async function loadTransfers() {
    const transfers = await apiRequest("/transfers");
    const container = document.getElementById("transfers");

    transfers.forEach(t => {
        container.innerHTML += `<div>
            ${t.sourceAmount} ${t.sourceCurrency} → ${t.targetAmount} ${t.targetCurrency}
        </div>`;
    });
}

loadBalances();
loadTransfers();
