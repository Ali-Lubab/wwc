import { apiRequest } from "./api.js";

// Currency symbols mapping
const currencySymbols = {
    'USD': '$',
    'EUR': '€',
    'GBP': '£',
    'JPY': '¥',
    'CHF': 'Fr',
    'CAD': 'C$',
    'AUD': 'A$'
};

function getCurrencySymbol(currency) {
    return currencySymbols[currency] || currency;
}

function formatAmount(amount, currency) {
    const symbol = getCurrencySymbol(currency);
    const formatted = parseFloat(amount).toLocaleString('en-US', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
    return `${symbol}${formatted}`;
}

// Ensure DOM is loaded before running any code
document.addEventListener("DOMContentLoaded", () => {
    loadAvailableCurrencies();

    document.getElementById("sourceCurrency").addEventListener("change", async (event) => {
        loadTargetCurrencies(event.target.value);
    });

    // CRITICAL FIX: Add form submission handler
    document.getElementById("calculatorForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await handleConversion();
    });
});

async function loadAvailableCurrencies() {
    const container = document.getElementById("sourceCurrency");
    container.innerHTML = '<option value="">Loading currencies...</option>';

    try {
        const currencies = await apiRequest("/rates/currencies");
        container.innerHTML = '';

        currencies.forEach(c => {
            const option = document.createElement("option");
            option.value = c;
            option.text = c;
            container.appendChild(option);
        });

        if (currencies.length > 0) {
            container.value = currencies[0];
            await loadTargetCurrencies(currencies[0]);
        }
    } catch (error) {
        container.innerHTML = '<option value="">Error loading currencies</option>';
    }
}

async function loadTargetCurrencies(sourceCurrency) {
    const container = document.getElementById("targetCurrency");
    container.innerHTML = '<option value="">Loading...</option>';

    try {
        const currencies = await apiRequest(`/rates/targetCurrencies/${sourceCurrency}`);
        container.innerHTML = '';

        currencies.forEach(c => {
            const option = document.createElement("option");
            option.value = c;
            option.text = c;
            container.appendChild(option);
        });

        if (currencies.length > 0) {
            container.value = currencies[0];
        }
    } catch (error) {
        container.innerHTML = '<option value="">Error loading currencies</option>';
    }
}

async function handleConversion() {
    const sourceCurrency = document.getElementById("sourceCurrency").value;
    const targetCurrency = document.getElementById("targetCurrency").value;
    const amount = document.getElementById("amount").value;
    const resultContainer = document.getElementById("result");
    const submitButton = document.querySelector('button[type="submit"]');

    if (!sourceCurrency || !targetCurrency || !amount) {
        resultContainer.innerHTML = `
            <div class="error-message">
                Please fill in all fields
            </div>
        `;
        return;
    }

    // Show loading state
    submitButton.disabled = true;
    submitButton.innerHTML = '<span class="spinner"></span> Converting...';
    resultContainer.innerHTML = '';

    try {
        const result = await apiRequest(
            `/rates/convert?source=${sourceCurrency}&target=${targetCurrency}&amount=${amount}`
        );

        // Display result
        resultContainer.innerHTML = `
            <div class="result-card">
                <div class="result-details">
                    ${formatAmount(amount, sourceCurrency)}
                </div>
                <div style="font-size: 2rem; margin: 16px 0;">↓</div>
                <div class="result-amount">
                    ${formatAmount(result.result, targetCurrency)}
                </div>
                <div class="result-details" style="margin-top: 16px;">
                    Exchange Rate: 1 ${sourceCurrency} = ${result.rate.toFixed(4)} ${targetCurrency}
                </div>
            </div>
        `;
    } catch (error) {
        resultContainer.innerHTML = `
            <div class="error-message">
                ${error.message}
            </div>
        `;
    } finally {
        // Reset button state
        submitButton.disabled = false;
        submitButton.innerHTML = 'Convert';
    }
}
