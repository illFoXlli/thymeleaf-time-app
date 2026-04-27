const clock = document.getElementById("clock");
const parts = clock.textContent.trim().split(" ");
let currentTime = new Date(parts[0] + "T" + parts[1]);
const timezone = parts.slice(2).join(" ");

setInterval(function () {
    currentTime.setSeconds(currentTime.getSeconds() + 1);

    const year = currentTime.getFullYear();
    const month = String(currentTime.getMonth() + 1).padStart(2, "0");
    const day = String(currentTime.getDate()).padStart(2, "0");
    const hours = String(currentTime.getHours()).padStart(2, "0");
    const minutes = String(currentTime.getMinutes()).padStart(2, "0");
    const seconds = String(currentTime.getSeconds()).padStart(2, "0");

    clock.textContent = year + "-" + month + "-" + day + " " +
        hours + ":" + minutes + ":" + seconds + " " + timezone;
}, 1000);
