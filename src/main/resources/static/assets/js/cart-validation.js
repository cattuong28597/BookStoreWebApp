
$(function () {
    $(`.quantity-product`).validate({
        onkeyup: function(element) { $(element).valid() },
        onclick: false,
        onfocusout: false,
        rules: {
            quantity: {
                required: true,
                min: 1,
                max: 1000
            }
        },
        errorLabelContainer: "#message-error-data",
        errorPlacement: function (error) {
            error.appendTo("#message-error-data");
        },
        showErrors: function(errorMap, errorList) {
            if (this.numberOfInvalids() > 0) {
                $("#message-error-data").removeClass("hide");
            } else {
                $("#message-error-data").addClass("hide");
            }
            this.defaultShowErrors();
        },
        messages: {
            quantity: {
                required: "Vui lòng nhập số lượng sản phẩm đầy đủ",
                min: "Giá sản phẩm tối thiểu 1",
                max: "Giá sản phẩm tối đa 1000"
            },
        }
    });

});