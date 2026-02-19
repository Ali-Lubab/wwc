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

async function loadBalances() {
    const container = document.getElementById("balances");

    try {
        const balances = await apiRequest("/balances");

        if (balances.length === 0) {
            container.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">💰</div>
                    <p>No balances available</p>
                </div>
            `;
            return;
        }

        container.innerHTML = '<div class="card-grid"></div>';
        const grid = container.querySelector('.card-grid');

        balances.forEach(b => {
            const card = document.createElement('div');
            card.className = 'card card-compact';
            card.innerHTML = `
                <div style="text-align: center;">
                    <div class="badge" style="margin-bottom: 8px;">${b.currency}</div>
                    <div style="font-size: 1.5rem; font-weight: 600; color: var(--color-primary-dark);">
                        ${formatAmount(b.amount, b.currency)}
                    </div>
                </div>
            `;
            grid.appendChild(card);
        });
    } catch (error) {
        container.innerHTML = `
            <div class="error-message">
                ${error.message}
            </div>
        `;
    }
}

async function loadTransfers() {
    const container = document.getElementById("transfers");

    try {
        const transfers = await apiRequest("/transfers");

        if (transfers.length === 0) {
            container.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">💸</div>
                    <p>No transfers yet</p>
                </div>
            `;
            return;
        }

        container.innerHTML = '';

        transfers.forEach(t => {
            const item = document.createElement('div');
            item.className = 'list-item';
            item.innerHTML = `
                <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 8px;">
                    <div>
                        <div style="font-weight: 500; color: var(--color-text-primary);">
                            ${formatAmount(t.sourceAmount, t.sourceCurrency)}
                        </div>
                        <div style="font-size: 0.875rem; color: var(--color-text-secondary);">
                            Recipient ID: ${t.recipientId}
                        </div>
                    </div>
                    <div style="text-align: right;">
                        <div style="font-weight: 500; color: var(--color-primary);">
                            ${formatAmount(t.targetAmount, t.targetCurrency)}
                        </div>
                        <div style="font-size: 0.875rem; color: var(--color-text-secondary);">
                            ${t.sourceCurrency} → ${t.targetCurrency}
                        </div>
                    </div>
                </div>
            `;
            container.appendChild(item);
        });
    } catch (error) {
        container.innerHTML = `
            <div class="error-message">
                ${error.message}
            </div>
        `;
    }
}

loadBalances();
loadTransfers();
