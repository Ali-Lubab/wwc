import { apiRequest } from "./api.js";

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

loadAvailableCurrencies();

document.getElementById("sourceCurrency").addEventListener("change", async (e) => {
    loadTargetCurrencies(event.target.value);
});
