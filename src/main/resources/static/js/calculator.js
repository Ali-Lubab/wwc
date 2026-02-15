import { apiRequest } from "./api.js";

// Ensure DOM is loaded before running any code
document.addEventListener("DOMContentLoaded", () => {
    loadAvailableCurrencies();

    document.getElementById("sourceCurrency").addEventListener("change", async (event) => {
        loadTargetCurrencies(event.target.value);
    });
});

async function loadAvailableCurrencies() {
    const currencies = await apiRequest("/rates/currencies")
    const container = document.getElementById("sourceCurrency")

    currencies.forEach(c => {
        const option = document.createElement("option");
        option.text = c;
        container.appendChild(option);
    });

    if (currencies.length > 0) {
        loadTargetCurrencies(currencies[0]);
    }
}

async function loadTargetCurrencies(sourceCurrency) {
    const currencies = await apiRequest(`/rates/targetCurrencies/${sourceCurrency}`);
    const container = document.getElementById("targetCurrency");
    container.options.length = 0;
    currencies.forEach( c => {
        const option = document.createElement("option");
        option.text = c;
        container.appendChild(option);
    });
}
