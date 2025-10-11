$(document).ready(function() {
  loadProducts();

  $('#saveProduct').click(function() {
    const product = {
      id: $('#productId').val(),
      name: $('#productName').val(),
      price: $('#productPrice').val(),
      description: $('#productDescription').val(),
      imageUrl: $('#productImage').val()
    };

    const url = product.id ? '/product/admin/update' : '/product/admin/insert';
    $.ajax({
      url: url,
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(product),
      success: function() {
        $('#addProductModal').modal('hide');
        loadProducts();
      }
    });
  });
});

function loadProducts() {
  $.get('/product/admin/list', function(products) {
    $('#productList').empty();
    products.forEach(p => {
      $('#productList').append(`
        <div class="col-md-4">
          <div class="card mb-3">
            <img src="${p.imageUrl || 'https://via.placeholder.com/150'}" class="card-img-top" alt="">
            <div class="card-body">
              <h5 class="card-title">${p.name}</h5>
              <p>${p.description || ''}</p>
              <p><b>${p.price.toLocaleString()} đ</b></p>
              <button class="btn btn-warning btn-sm" onclick="editProduct('${p.id}')">Sửa</button>
              <button class="btn btn-danger btn-sm" onclick="deleteProduct('${p.id}')">Xóa</button>
            </div>
          </div>
        </div>
      `);
    });
  });
}


function editProduct(id) {
  $.get('/product/admin/list', function(products) {
    const p = products.find(x => x.id === id);
    if (p) {
      $('#productId').val(p.id);
      $('#productName').val(p.name);
      $('#productPrice').val(p.price);
      $('#productDescription').val(p.description);
      $('#productImage').val(p.imageUrl);
      $('#addProductModal').modal('show');
    }
  });
}

function deleteProduct(id) {
  if (confirm('Bạn có chắc muốn xóa sản phẩm này không?')) {
    $.post(`/product/admin/delete/${id}`, function() {
      loadProducts();
    });
  }
}
