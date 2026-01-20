// ===========================
// AUTH + COMMON UTILS
// ===========================


// ================= TOKEN HELPERS =================
function getToken() {
    return localStorage.getItem("token");
}

function getRole() {
    return localStorage.getItem("role");
}

// ================= AUTH HEADERS =================
function authHeaders() {
    const token = getToken();
    if (!token) return {};

    return {
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };
}


// ================= AUTH FETCH (OPTIONAL) =================
function authFetch(url, options = {}) {
    return fetch(url, {
        ...options,
        headers: {
            ...authHeaders(),
            ...(options.headers || {})
        }
    });
}

// ================= LOGOUT =================
function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}

// ================= AUTH GUARD =================
function requireAuth() {
    const token = getToken();
    const role = getRole();

    if (!token || !role) {
        window.location.href = "/login.html";
        return;
    }

    const path = window.location.pathname;

    if (path.includes("admin") && role !== "ADMIN") {
        window.location.href = "/login.html";
    }
    if (path.includes("customer") && role !== "CUSTOMER") {
        window.location.href = "/login.html";
    }
    if (path.includes("professional") && role !== "PROFESSIONAL") {
        window.location.href = "/login.html";
    }
}
	
function renderUserInfo() {
    const name = localStorage.getItem("fullName");
    const role = localStorage.getItem("role");

    console.log("Rendering user:", name, role);

    const el = document.getElementById("userInfo");
    if (el && name && role) {
        el.innerText = `👤 ${name} (${role})`;
    }
}

let confirmCallback = null;

function showConfirm(message, onConfirm) {
    document.getElementById("confirmMessage").innerText = message;
    confirmCallback = onConfirm;

    const modal = new bootstrap.Modal(
        document.getElementById("confirmModal")
    );
    modal.show();

    document.getElementById("confirmYesBtn").onclick = () => {
        modal.hide();
        if (confirmCallback) confirmCallback();
    };
}

function showToast(message, type = "success") {

    const toastEl = document.getElementById("appToast");
    const msgEl = document.getElementById("toastMessage");
    const iconEl = document.getElementById("toastIcon");

    msgEl.innerText = message;

    // Reset styles
    toastEl.classList.remove(
        "bg-success", "bg-danger", "bg-warning", "bg-info"
    );

    // Set color + icon
    if (type === "error") {
        toastEl.classList.add("bg-danger");
        iconEl.innerText = "❌";
    }
    else if (type === "warning") {
        toastEl.classList.add("bg-warning");
        iconEl.innerText = "⚠️";
    }
    else if (type === "info") {
        toastEl.classList.add("bg-info");
        iconEl.innerText = "ℹ️";
    }
    else {
        toastEl.classList.add("bg-success");
        iconEl.innerText = "✅";
    }

    const toast = new bootstrap.Toast(toastEl, { delay: 2500 });
    toast.show();
}

function formatDuration(totalMinutes) {
    if (!totalMinutes || totalMinutes <= 0) return "0 mins";

    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    if (hours > 0 && minutes > 0) {
        return `${hours} hrs ${minutes} mins`;
    }
    if (hours > 0) {
        return `${hours} hrs`;
    }
    return `${minutes} mins`;
}

function formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return "";

    const date = new Date(dateTimeStr);

    return date.toLocaleString("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        hour12: true
    });
}

function getCellValue(td) {
    if (!td) return "";

    // 1️⃣ Date / numeric sorting via data-value (highest priority)
    if (td.dataset && td.dataset.value) {
        return Number(td.dataset.value);
    }

    // 2️⃣ Badge values (status, service count)
    const badge = td.querySelector?.(".badge");
    if (badge) {
        const val = badge.innerText.trim();
        return isNaN(val) ? val.toLowerCase() : Number(val);
    }

    // 3️⃣ Ignore action buttons
    if (td.querySelector?.("button")) return "";

    const text = td.innerText.trim();

    // 4️⃣ Numeric
    if (!isNaN(text)) return Number(text);

    // 5️⃣ Fallback string
    return text.toLowerCase();
}


function makeTableSortable(table) {
    if (!table) return;

    const thead = table.querySelector("thead");
    const tbody = table.tBodies[0];

    if (!thead || !tbody) return;

    // prevent duplicate binding
    if (thead.dataset.sortableAttached === "true") return;
    thead.dataset.sortableAttached = "true";

    let sortState = {};

    thead.addEventListener("click", e => {
        const th = e.target.closest("th.sortable");
        if (!th) return;

        const colIndex = Array.from(th.parentNode.children).indexOf(th);
        const asc = !(sortState[colIndex] === true);
        sortState[colIndex] = asc;

        const rows = Array.from(tbody.rows);

        rows.sort((rowA, rowB) => {
            const A = getCellValue(rowA.cells[colIndex]);
            const B = getCellValue(rowB.cells[colIndex]);

            if (typeof A === "number" && typeof B === "number") {
                return asc ? A - B : B - A;
            }

            return asc
                ? String(A).localeCompare(String(B))
                : String(B).localeCompare(String(A));
        });

        rows.forEach(r => tbody.appendChild(r));
    });
}

function initServicePopovers() {
    document.querySelectorAll(".service-badge").forEach(el => {

        // destroy existing instance (important on reload)
        bootstrap.Popover.getInstance(el)?.dispose();

        new bootstrap.Popover(el, {
            trigger: "focus",
            placement: "top",
            html: true,
            content: `<ol class="mb-0">
                ${el.dataset.services
                    ?.split(",")
                    .map(s => `<li>${s}</li>`)
                    .join("") || ""}
            </ol>`
        });
    });
}

function getStatusBadge(status) {
    const colorMap = {
        CREATED: "warning",
        ACCEPTED: "info",
        IN_PROGRESS: "primary",
        COMPLETED: "success",
        CANCELLED: "secondary"
    };

    const color = colorMap[status] || "secondary";
    return `<span class="badge bg-${color}">${status}</span>`;
}

function loadNavbar() {
    const container = document.getElementById("navbar");
    if (!container) return;

    fetch("/navbar.html")
        .then(res => res.text())
        .then(html => {
            container.innerHTML = html;
            renderUserInfo();
        });
}
