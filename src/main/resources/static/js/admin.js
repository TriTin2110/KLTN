// Dữ liệu sản phẩm mẫu (từ menu cà phê)
let products = [
    { id: 1, name: 'Cà Phê Đen Đá', price: 15000, description: 'Cà phê đen đá truyền thống', image: 'images/drink/caphedenda.jpg', category: 'ca-phe' },
    { id: 2, name: 'Cà Phê Sữa Đá', price: 16000, description: 'Cà phê sữa đá Việt Nam', image: 'https://via.placeholder.com/80?text=Milk+Ice', category: 'ca-phe' },
    { id: 3, name: 'Bạc Xỉu', price: 20000, description: 'Cà phê sữa ít cà phê', image: 'https://via.placeholder.com/80?text=Bac+Xiu', category: 'ca-phe' },
    { id: 4, name: 'Cà Phê Sữa Tươi', price: 22000, description: 'Cà phê sáng tạo với sữa tươi', image: 'https://via.placeholder.com/80?text=Fresh+Milk', category: 'ca-phe' },
    { id: 5, name: 'Matcha Latte', price: 22000, description: 'Matcha latte xanh mịn', image: 'https://via.placeholder.com/80?text=Matcha', category: 'matcha' },
    { id: 6, name: 'Trà Đào Hạt Dắc', price: 25000, description: 'Trà đào cam quýt tươi mát', image: 'https://via.placeholder.com/80?text=Peach+Tea', category: 'tra-trai-cay' }
];

// Render danh sách sản phẩm
function renderProducts(productsToRender = products) {
    const productList = document.getElementById('productList');
    productList.innerHTML = '';
    productsToRender.forEach(product => {
        const col = document.createElement('div');
        col.className = 'col-md-4';
        col.innerHTML = `
            <div class="admin-product-card">
                <div class="d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                        <div class="admin-product-image me-3" style="background-image: url('${product.image}'); background-size: cover;"></div>
                        <div>
                            <h5>${product.name}</h5>
                            <p class="mb-1">${product.description}</p>
                            <strong>${product.price.toLocaleString()}đ</strong>
                            <br><small class="text-muted">Danh mục: ${product.category}</small>
                        </div>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-warning me-1" onclick="editProduct(${product.id})"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-sm btn-danger" onclick="deleteProduct(${product.id})"><i class="fas fa-trash"></i></button>
                    </div>
                </div>
            </div>
        `;
        productList.appendChild(col);
    });
    document.getElementById('total-products').textContent = products.length;
}

// Thêm sản phẩm
document.getElementById('saveProduct').addEventListener('click', () => {
    const id = document.getElementById('productId').value;
    const name = document.getElementById('productName').value;
    const price = parseInt(document.getElementById('productPrice').value);
    const description = document.getElementById('productDescription').value;
    const image = document.getElementById('productImage').value || 'https://via.placeholder.com/80?text=No+Image';
    const category = document.getElementById('productCategory').value;

    if (id) {
        // Sửa
        const product = products.find(p => p.id == id);
        if (product) {
            product.name = name;
            product.price = price;
            product.description = description;
            product.image = image;
            product.category = category;
        }
    } else {
        // Thêm mới
        const newId = Math.max(...products.map(p => p.id)) + 1;
        products.push({ id: newId, name, price, description, image, category });
    }

    bootstrap.Modal.getInstance(document.getElementById('addProductModal')).hide();
    renderProducts();
    document.getElementById('productForm').reset();
    document.getElementById('modalTitle').textContent = 'Thêm Sản Phẩm Mới';
    document.getElementById('productId').value = '';
});

// Sửa sản phẩm
function editProduct(id) {
    const product = products.find(p => p.id == id);
    if (product) {
        document.getElementById('productId').value = product.id;
        document.getElementById('productName').value = product.name;
        document.getElementById('productPrice').value = product.price;
        document.getElementById('productDescription').value = product.description;
        document.getElementById('productImage').value = product.image;
        document.getElementById('productCategory').value = product.category;
        document.getElementById('modalTitle').textContent = 'Sửa Sản Phẩm';
        new bootstrap.Modal(document.getElementById('addProductModal')).show();
    }
}

// Xóa sản phẩm
function deleteProduct(id) {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
        products = products.filter(p => p.id != id);
        renderProducts();
    }
}

// Tìm kiếm
document.getElementById('searchInput').addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    const filtered = products.filter(p => 
        p.name.toLowerCase().includes(searchTerm) || 
        p.description.toLowerCase().includes(searchTerm) ||
        p.category.includes(searchTerm)
    );
    renderProducts(filtered);
});

// Tìm kiếm toàn cục (có thể mở rộng sau)
function performGlobalSearch() {
    const term = document.getElementById('globalSearch').value.toLowerCase();
    alert('Tìm kiếm toàn cục: ' + term); // Placeholder, có thể tích hợp với các section khác
}

// Navigation
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
        link.classList.add('active');
        document.querySelectorAll('.admin-section').forEach(s => s.classList.add('d-none'));
        document.querySelector(link.getAttribute('href')).classList.remove('d-none');
    });
});

// Khởi tạo
renderProducts();