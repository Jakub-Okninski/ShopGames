document.addEventListener("DOMContentLoaded", function () {
    var searchDetails = document.getElementById("serachDetails");
    var moreCheckbox = document.getElementById("moreCheckbox");

    // Ukryj panel przy załadowaniu strony
    searchDetails.style.display = moreCheckbox.checked ? "flex" : "none";

    // Obsługa zmiany stanu checkboxa "Więcej"
    moreCheckbox.addEventListener("change", function () {
        searchDetails.style.display = this.checked ? "flex" : "none";
    });
});