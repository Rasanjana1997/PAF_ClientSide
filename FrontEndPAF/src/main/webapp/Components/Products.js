$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});


// CLIENT MODEL...............................................................................
function validateProductForm() {
	
    // Code Validation.................................................
    if ($("#productCode").val().trim() == "") {
        return "Insert Product Code.";
    }
    // Name Validation.................................................
    if ($("#productName").val().trim() == "") {
        return "Insert Product Name.";
    }
    // Price Validation.................................................
    if ($("#productPrice").val().trim() == "") {
        return "Insert Product Price.";
    }
    // check is it a numeric value
    var tmpPrice = $("#productPrice").val().trim();
    if (!$.isNumeric(tmpPrice)) {
        return "Insert a numerical value for Product Price.";
    }
    // convert to decimal price
    $("#productPrice").val(parseFloat(tmpPrice).toFixed(2));
    
    // Description Validation...........................................
    if ($("#productDesc").val().trim() == "") {
        return "Insert Product Description.";
    }
    return true;
}

$(document).on("click", "#btnSave", function (event) {
	
    // Clear............................
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    
    // Validate Form....................
    var status = validateProductForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    
    // If It Is Valid..................................................
    var type = ($("#hidProductIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "ProductsAPI",
            type: type,
            data: $("#formProduct").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onProductSaveComplete(response.responseText, status);
            }
        });
});

function onProductSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $("#alertSuccess").text("Successfully saved.");
            $("#alertSuccess").show();
            $("#divProductsGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while saving.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while saving..");
        $("#alertError").show();
    }
    $("#hidProductIDSave").val("");
    $("#formProduct")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hidProductIDSave").val($(this).data("productid"));
    $("#productCode").val($(this).closest("tr").find('td:eq(0)').text());
    $("#productName").val($(this).closest("tr").find('td:eq(1)').text());
    $("#productPrice").val($(this).closest("tr").find('td:eq(2)').text());
    $("#productDesc").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function (event) {
    $.ajax(
        {
            url: "ProductsAPI",
            type: "DELETE",
            data: "productID=" + $(this).data("productid"),
            dataType: "text",
            complete: function (response, status) {
                onItemDeleteComplete(response.responseText, status);
            }
        });
});

//
function onProductDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divProductsGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
}