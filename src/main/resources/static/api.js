// ===========================
// AUTH + COMMON UTILS
// ===========================

function getToken() {
    return localStorage.getItem("token");
}

function authHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + getToken()
    };
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}

function requireAuth() {
    if (!getToken()) {
        window.location.href = "/login.html";
    }
}
