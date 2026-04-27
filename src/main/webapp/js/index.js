const form = document.getElementById("timeForm");
const input = document.getElementById("tzInput");
const timeUrl = form.dataset.timeUrl || "/time";

form.addEventListener("submit", function (e) {
    e.preventDefault();

    const value = input.value.trim();

    if (!value) {
        window.location.href = timeUrl;
        return;
    }

    const sign = value.startsWith("-") ? "" : "+";
    const timezone = "UTC" + sign + value;

    window.location.href = timeUrl + "?timezone=" + encodeURIComponent(timezone);
});
