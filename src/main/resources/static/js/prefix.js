function prefix(id, prefix) {
    $("#" + id).keydown(function (e) {
        var oldvalue = $(this).val();
        var field = this;
        setTimeout(function () {
            if (field.value.indexOf(prefix) !== 0) {
                $(field).val(oldvalue);
            }
        }, 1);
    });
}
