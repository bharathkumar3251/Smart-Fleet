const API_BASE = "";

// Show alert banner
function showAlert(message, isError = false) {
    const banner = document.getElementById("alertBanner");
    banner.className = `alert-banner ${isError ? 'alert-error' : 'alert-success'}`;
    banner.innerText = message;
    banner.style.display = "block";
    setTimeout(() => {
        banner.style.display = "none";
    }, 4000);
}

// Module Tab Switcher
function showModule(moduleId) {
    document.querySelectorAll(".module-section").forEach(sec => sec.classList.remove("active"));
    document.querySelectorAll(".nav-btn").forEach(btn => btn.classList.remove("active"));
    
    document.getElementById(moduleId).classList.add("active");
    event.target.classList.add("active");

    // Load data for active module
    if (moduleId === "vehicles") loadVehicles();
    if (moduleId === "tires") { loadVehicles(); loadTires(); }
    if (moduleId === "telemetry") loadTiresDropdown();
    if (moduleId === "alerts") loadAlerts();
    if (moduleId === "contacts") loadContacts();
    if (moduleId === "products") loadProducts();
    if (moduleId === "procurement") { loadContacts(); loadProducts(); loadPOs(); loadVendorBills(); }
    if (moduleId === "sales") { loadContacts(); loadProducts(); loadSOs(); loadCustomerInvoices(); }
    if (moduleId === "accounting") { loadAccounts(); loadJournalEntries(); }
    if (moduleId === "budgets") { loadAnalyticAccounts(); }
    if (moduleId === "reports") { loadBalanceSheet(); loadProfitLoss(); loadBudgetReport(); }
}

// 1. VEHICLES
async function loadVehicles() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/vehicles`);
        const vehicles = await res.json();
        
        // Populate Table
        const tbody = document.getElementById("vehicleTable");
        tbody.innerHTML = vehicles.map(v => `
            <tr>
                <td>${v.id}</td>
                <td><strong>${v.vehicleNumber}</strong></td>
                <td>${v.vehicleModel}</td>
                <td>${v.vehicleType}</td>
                <td><span class="badge badge-active">${v.status}</span></td>
            </tr>
        `).join("");

        // Populate Dropdowns in other tabs
        const select = document.getElementById("tVehicleSelect");
        if (select) {
            select.innerHTML = vehicles.map(v => `<option value="${v.id}">${v.vehicleNumber} (${v.vehicleModel})</option>`).join("");
        }
    } catch (err) {
        console.error("Error loading vehicles:", err);
    }
}

document.getElementById("vehicleForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        vehicleNumber: document.getElementById("vNumber").value,
        vehicleModel: document.getElementById("vModel").value,
        vehicleType: document.getElementById("vType").value,
        status: "ACTIVE"
    };
    try {
        const res = await fetch(`${API_BASE}/api/fleet/vehicles`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Vehicle created successfully!");
            document.getElementById("vehicleForm").reset();
            loadVehicles();
        }
    } catch (err) {
        showAlert("Failed to create vehicle", true);
    }
});

// 2. TIRES
async function loadTires() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/tires`);
        const tires = await res.json();
        const tbody = document.getElementById("tireTable");
        tbody.innerHTML = tires.map(t => `
            <tr>
                <td>${t.id}</td>
                <td><strong>${t.serialNumber}</strong></td>
                <td>${t.brand} (${t.size})</td>
                <td>${t.vehicle ? t.vehicle.vehicleNumber : 'N/A'}</td>
                <td>${t.position}</td>
                <td style="color: ${t.currentPsi < 28 ? '#2563eb' : '#000000'}; font-weight: bold;">
                    ${t.currentPsi != null ? t.currentPsi : 'N/A'} PSI
                </td>
            </tr>
        `).join("");
    } catch (err) {
        console.error("Error loading tires:", err);
    }
}

document.getElementById("tireForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        serialNumber: document.getElementById("tSerial").value,
        brand: document.getElementById("tBrand").value,
        size: document.getElementById("tSize").value,
        vehicleId: parseInt(document.getElementById("tVehicleSelect").value),
        position: document.getElementById("tPosition").value
    };
    try {
        const res = await fetch(`${API_BASE}/api/fleet/tires`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Tire added successfully!");
            document.getElementById("tireForm").reset();
            loadTires();
        }
    } catch (err) {
        showAlert("Failed to add tire", true);
    }
});

// 3. TELEMETRY
async function loadTiresDropdown() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/tires`);
        const tires = await res.json();
        const select = document.getElementById("telemetryTireSelect");
        select.innerHTML = tires.map(t => `
            <option value="${t.id}">${t.serialNumber} - ${t.vehicle ? t.vehicle.vehicleNumber : 'No Vehicle'} (${t.position})</option>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

document.getElementById("telemetryForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const psi = parseFloat(document.getElementById("telemetryPsi").value);
    const data = {
        tireId: parseInt(document.getElementById("telemetryTireSelect").value),
        psi: psi,
        temperature: parseFloat(document.getElementById("telemetryTemp").value)
    };
    try {
        const res = await fetch(`${API_BASE}/api/fleet/telemetry`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            if (psi < 28) {
                showAlert(`Telemetry recorded! PSI is ${psi} (< 28) - Maintenance Ticket Automatically Triggered!`, true);
            } else {
                showAlert(`Telemetry recorded successfully! PSI is ${psi} (Normal).`);
            }
            document.getElementById("telemetryForm").reset();
        }
    } catch (err) {
        showAlert("Failed to submit telemetry", true);
    }
});

// 4. MAINTENANCE ALERTS
async function loadAlerts() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/maintenance-alerts`);
        const alerts = await res.json();
        const tbody = document.getElementById("alertsTable");
        tbody.innerHTML = alerts.map(a => `
            <tr>
                <td>#${a.ticketId}</td>
                <td><strong>${a.vehicleNumber}</strong></td>
                <td>${a.tireSerial}</td>
                <td><span style="color: #2563eb; font-weight: bold;">${a.reason}</span></td>
                <td>${a.triggerPsi} PSI</td>
                <td><span class="badge badge-open">${a.status}</span></td>
                <td>${a.createdAt ? new Date(a.createdAt).toLocaleString() : 'N/A'}</td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

// 5. CONTACTS
async function loadContacts() {
    try {
        const res = await fetch(`${API_BASE}/api/contacts`);
        const contacts = await res.json();
        const tbody = document.getElementById("contactTable");
        tbody.innerHTML = contacts.map(c => `
            <tr>
                <td>${c.id}</td>
                <td><strong>${c.name}</strong></td>
                <td>${c.type}</td>
                <td>${c.phone || '-'}</td>
                <td>${c.email || '-'}</td>
            </tr>
        `).join("");

        // Populate PO vendor select & SO customer select
        const poVendors = contacts.filter(c => c.type === 'VENDOR');
        const poSelect = document.getElementById("poVendorSelect");
        if (poSelect) poSelect.innerHTML = poVendors.map(v => `<option value="${v.id}">${v.name}</option>`).join("");

        const soCustomers = contacts.filter(c => c.type === 'CUSTOMER');
        const soSelect = document.getElementById("soCustomerSelect");
        if (soSelect) soSelect.innerHTML = soCustomers.map(c => `<option value="${c.id}">${c.name}</option>`).join("");

    } catch (err) {
        console.error(err);
    }
}

document.getElementById("contactForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById("cName").value,
        type: document.getElementById("cType").value,
        phone: document.getElementById("cPhone").value,
        email: document.getElementById("cEmail").value
    };
    try {
        const res = await fetch(`${API_BASE}/api/contacts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Contact saved!");
            document.getElementById("contactForm").reset();
            loadContacts();
        }
    } catch (err) {
        showAlert("Failed to save contact", true);
    }
});

// 6. PRODUCTS
async function loadProducts() {
    try {
        const res = await fetch(`${API_BASE}/api/products`);
        const products = await res.json();
        const tbody = document.getElementById("productTable");
        tbody.innerHTML = products.map(p => `
            <tr>
                <td>${p.id}</td>
                <td><strong>${p.name}</strong></td>
                <td>${p.type}</td>
                <td>&#8377; ${p.price}</td>
                <td>${p.quantity}</td>
            </tr>
        `).join("");

        const poProdSelect = document.getElementById("poProductSelect");
        if (poProdSelect) poProdSelect.innerHTML = products.map(p => `<option value="${p.id}">${p.name} (&#8377;${p.price})</option>`).join("");

        const soProdSelect = document.getElementById("soProductSelect");
        if (soProdSelect) soProdSelect.innerHTML = products.map(p => `<option value="${p.id}">${p.name} (&#8377;${p.price})</option>`).join("");

    } catch (err) {
        console.error(err);
    }
}

document.getElementById("productForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById("pName").value,
        type: document.getElementById("pType").value,
        price: parseFloat(document.getElementById("pPrice").value),
        quantity: parseFloat(document.getElementById("pQty").value)
    };
    try {
        const res = await fetch(`${API_BASE}/api/products`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Product saved!");
            document.getElementById("productForm").reset();
            loadProducts();
        }
    } catch (err) {
        showAlert("Failed to save product", true);
    }
});

// 7. PROCUREMENT
async function loadPOs() {
    try {
        const res = await fetch(`${API_BASE}/api/procurement/purchase-orders`);
        const pos = await res.json();
        const tbody = document.getElementById("poTable");
        tbody.innerHTML = pos.map(po => `
            <tr>
                <td>#${po.id}</td>
                <td>${po.vendor ? po.vendor.name : 'N/A'}</td>
                <td>&#8377; ${po.total}</td>
                <td><span class="badge ${po.status === 'BILLED' ? 'badge-billed' : 'badge-open'}">${po.status}</span></td>
                <td>
                    ${po.status === 'CREATED' ? `<button class="btn-submit" style="padding: 4px 8px; font-size: 0.8rem;" onclick="convertToVendorBill(${po.id})">Convert to Bill</button>` : 'Billed'}
                </td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

document.getElementById("poForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        vendorId: parseInt(document.getElementById("poVendorSelect").value),
        productId: parseInt(document.getElementById("poProductSelect").value),
        quantity: parseFloat(document.getElementById("poQty").value),
        unitPrice: parseFloat(document.getElementById("poPrice").value)
    };
    try {
        const res = await fetch(`${API_BASE}/api/procurement/purchase-orders`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Purchase Order Created!");
            document.getElementById("poForm").reset();
            loadPOs();
        }
    } catch (err) {
        showAlert("Failed to create PO", true);
    }
});

async function convertToVendorBill(poId) {
    try {
        const res = await fetch(`${API_BASE}/api/procurement/vendor-bills`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ purchaseOrderId: poId })
        });
        if (res.ok) {
            showAlert("Vendor Bill & Accounting Journal Entry Generated!");
            loadPOs();
            loadVendorBills();
        }
    } catch (err) {
        showAlert("Failed to convert to bill", true);
    }
}

async function loadVendorBills() {
    try {
        const res = await fetch(`${API_BASE}/api/procurement/vendor-bills`);
        const bills = await res.json();
        const tbody = document.getElementById("vendorBillTable");
        tbody.innerHTML = bills.map(b => `
            <tr>
                <td>#${b.id}</td>
                <td>#${b.purchaseOrder ? b.purchaseOrder.id : 'N/A'}</td>
                <td>${b.vendor ? b.vendor.name : 'N/A'}</td>
                <td>&#8377; ${b.amount}</td>
                <td><span class="badge ${b.status === 'PAID' ? 'badge-paid' : 'badge-unpaid'}">${b.status}</span></td>
                <td>
                    ${b.status === 'UNPAID' ? `<button class="btn-submit" style="padding: 4px 8px; font-size: 0.8rem;" onclick="payVendorBill(${b.id}, ${b.amount})">Pay Bill</button>` : 'Paid'}
                </td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

async function payVendorBill(billId, amount) {
    try {
        const res = await fetch(`${API_BASE}/api/procurement/payments`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ vendorBillId: billId, amount: amount, paymentMode: "BANK" })
        });
        if (res.ok) {
            showAlert("Payment processed & Bank Accounting Entry Updated!");
            loadVendorBills();
        }
    } catch (err) {
        showAlert("Failed to process payment", true);
    }
}

// 8. SALES
async function loadSOs() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/sales-orders`);
        const sos = await res.json();
        const tbody = document.getElementById("soTable");
        tbody.innerHTML = sos.map(so => `
            <tr>
                <td>#${so.id}</td>
                <td>${so.customer ? so.customer.name : 'N/A'}</td>
                <td>&#8377; ${so.total}</td>
                <td><span class="badge ${so.status === 'INVOICED' ? 'badge-invoiced' : 'badge-open'}">${so.status}</span></td>
                <td>
                    ${so.status === 'CREATED' ? `<button class="btn-submit" style="padding: 4px 8px; font-size: 0.8rem;" onclick="createCustomerInvoice(${so.id})">Create Invoice</button>` : 'Invoiced'}
                </td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

document.getElementById("soForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        customerId: parseInt(document.getElementById("soCustomerSelect").value),
        productId: parseInt(document.getElementById("soProductSelect").value),
        quantity: parseFloat(document.getElementById("soQty").value),
        unitPrice: parseFloat(document.getElementById("soPrice").value)
    };
    try {
        const res = await fetch(`${API_BASE}/api/fleet/sales-orders`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Sales Order Created!");
            document.getElementById("soForm").reset();
            loadSOs();
        }
    } catch (err) {
        showAlert("Failed to create Sales Order", true);
    }
});

async function createCustomerInvoice(soId) {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/customer-invoices`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ salesOrderId: soId })
        });
        if (res.ok) {
            showAlert("Customer Invoice & Revenue Journal Entry Generated!");
            loadSOs();
            loadCustomerInvoices();
        }
    } catch (err) {
        showAlert("Failed to create invoice", true);
    }
}

async function loadCustomerInvoices() {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/customer-invoices`);
        const invoices = await res.json();
        const tbody = document.getElementById("customerInvoiceTable");
        tbody.innerHTML = invoices.map(inv => `
            <tr>
                <td>#${inv.id}</td>
                <td>#${inv.salesOrder ? inv.salesOrder.id : 'N/A'}</td>
                <td>${inv.customer ? inv.customer.name : 'N/A'}</td>
                <td>&#8377; ${inv.amount}</td>
                <td><span class="badge ${inv.status === 'PAID' ? 'badge-paid' : 'badge-unpaid'}">${inv.status}</span></td>
                <td>
                    ${inv.status === 'UNPAID' ? `<button class="btn-submit" style="padding: 4px 8px; font-size: 0.8rem;" onclick="receiveCustomerPayment(${inv.id}, ${inv.amount})">Receive Payment</button>` : 'Paid'}
                </td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

async function receiveCustomerPayment(invoiceId, amount) {
    try {
        const res = await fetch(`${API_BASE}/api/fleet/customer-payments`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ customerInvoiceId: invoiceId, amount: amount, paymentMode: "BANK" })
        });
        if (res.ok) {
            showAlert("Customer Payment Received & Bank Ledger Updated!");
            loadCustomerInvoices();
        }
    } catch (err) {
        showAlert("Failed to receive payment", true);
    }
}

// 9. ACCOUNTING
async function loadAccounts() {
    try {
        const res = await fetch(`${API_BASE}/api/accounting/accounts`);
        const accounts = await res.json();
        const tbody = document.getElementById("accountTable");
        tbody.innerHTML = accounts.map(a => `
            <tr>
                <td>${a.id}</td>
                <td><strong>${a.name}</strong></td>
                <td>${a.accountType}</td>
                <td style="font-weight: bold; color: ${a.balance >= 0 ? '#000000' : '#2563eb'}">&#8377; ${a.balance}</td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

async function loadJournalEntries() {
    try {
        const res = await fetch(`${API_BASE}/api/accounting/journal-entries`);
        const entries = await res.json();
        const tbody = document.getElementById("journalTable");
        tbody.innerHTML = entries.map(j => `
            <tr>
                <td>#${j.id}</td>
                <td><strong>${j.reference}</strong></td>
                <td>${j.description}</td>
                <td>${j.entryDate ? new Date(j.entryDate).toLocaleString() : '-'}</td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

// 10. BUDGETS
async function loadAnalyticAccounts() {
    try {
        const res = await fetch(`${API_BASE}/api/analytic-accounts`);
        const analytics = await res.json();
        const select = document.getElementById("budgetAnalyticSelect");
        if (select) {
            select.innerHTML = analytics.map(a => `<option value="${a.id}">${a.name}</option>`).join("");
        }
    } catch (err) {
        console.error(err);
    }
}

document.getElementById("analyticForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById("anName").value,
        description: document.getElementById("anDesc").value
    };
    try {
        const res = await fetch(`${API_BASE}/api/analytic-accounts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Analytic Division Created!");
            document.getElementById("analyticForm").reset();
            loadAnalyticAccounts();
        }
    } catch (err) {
        showAlert("Failed to create division", true);
    }
});

document.getElementById("budgetForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = {
        analyticAccount: { id: parseInt(document.getElementById("budgetAnalyticSelect").value) },
        allocatedBudget: parseFloat(document.getElementById("budgetAmount").value),
        period: document.getElementById("budgetPeriod").value
    };
    try {
        const res = await fetch(`${API_BASE}/api/budgets`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert("Budget Allocated!");
            document.getElementById("budgetForm").reset();
        }
    } catch (err) {
        showAlert("Failed to allocate budget", true);
    }
});

// 11. REPORTS
async function loadBalanceSheet() {
    try {
        const res = await fetch(`${API_BASE}/api/reports/balance-sheet`);
        const bs = await res.json();
        let html = "<h4>ASSETS</h4><table>";
        for (let [acc, bal] of Object.entries(bs.assets || {})) {
            html += `<tr><td>${acc}</td><td style="text-align: right;">&#8377; ${bal}</td></tr>`;
        }
        html += `<tr style="font-weight: bold; border-top: 2px solid #2563eb;"><td>Total Assets</td><td style="text-align: right;">&#8377; ${bs.totalAssets}</td></tr></table>`;

        html += "<h4 style='margin-top: 20px;'>LIABILITIES</h4><table>";
        for (let [acc, bal] of Object.entries(bs.liabilities || {})) {
            html += `<tr><td>${acc}</td><td style="text-align: right;">&#8377; ${bal}</td></tr>`;
        }
        html += `<tr style="font-weight: bold; border-top: 2px solid #2563eb;"><td>Total Liabilities</td><td style="text-align: right;">&#8377; ${bs.totalLiabilities}</td></tr></table>`;
        html += `<h4 style="margin-top: 20px; color: #2563eb;">EQUITY: &#8377; ${bs.equity}</h4>`;

        document.getElementById("balanceSheetView").innerHTML = html;
    } catch (err) {
        console.error(err);
    }
}

async function loadProfitLoss() {
    try {
        const res = await fetch(`${API_BASE}/api/reports/profit-loss`);
        const pl = await res.json();
        let html = "<h4>INCOME</h4><table>";
        for (let [acc, bal] of Object.entries(pl.income || {})) {
            html += `<tr><td>${acc}</td><td style="text-align: right;">&#8377; ${bal}</td></tr>`;
        }
        html += `<tr style="font-weight: bold; border-top: 2px solid #2563eb;"><td>Total Income</td><td style="text-align: right;">&#8377; ${pl.totalIncome}</td></tr></table>`;

        html += "<h4 style='margin-top: 20px;'>EXPENSES</h4><table>";
        for (let [acc, bal] of Object.entries(pl.expenses || {})) {
            html += `<tr><td>${acc}</td><td style="text-align: right;">&#8377; ${bal}</td></tr>`;
        }
        html += `<tr style="font-weight: bold; border-top: 2px solid #2563eb;"><td>Total Expenses</td><td style="text-align: right;">&#8377; ${pl.totalExpenses}</td></tr></table>`;
        html += `<h4 style="margin-top: 20px; color: ${pl.netProfit >= 0 ? '#000000' : '#2563eb'};">NET PROFIT: &#8377; ${pl.netProfit}</h4>`;

        document.getElementById("profitLossView").innerHTML = html;
    } catch (err) {
        console.error(err);
    }
}

async function loadBudgetReport() {
    try {
        const res = await fetch(`${API_BASE}/api/reports/budget`);
        const budgets = await res.json();
        const tbody = document.getElementById("budgetReportTable");
        tbody.innerHTML = budgets.map(b => `
            <tr>
                <td><strong>${b.fleetDivision}</strong></td>
                <td>&#8377; ${b.allocatedBudget}</td>
                <td>&#8377; ${b.actualExpense}</td>
                <td style="font-weight: bold; color: ${b.variance >= 0 ? '#000000' : '#2563eb'}">&#8377; ${b.variance}</td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
    }
}

// Initialize on page load
window.onload = () => {
    loadVehicles();
};
