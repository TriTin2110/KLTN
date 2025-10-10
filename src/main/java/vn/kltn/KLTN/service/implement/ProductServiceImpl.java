package vn.kltn.KLTN.service.implement;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Category;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Coupon;
import vn.kltn.KLTN.entity.Event;
import vn.kltn.KLTN.entity.Ingredient;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.ProductDetail;
import vn.kltn.KLTN.entity.Supplier;
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.service.CategoryService;
import vn.kltn.KLTN.service.IngredientService;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.SupplierService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryService categoryService;

	@Override
	@Transactional
	public boolean add(Product product) {
		// TODO Auto-generated method stub
		try {
			if (product.getIngredients().isEmpty() || product.getProductDetail() == null
					|| product.getCategory() == null || product.getPrices().isEmpty())
				return false;

			repository.save(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean remove(String productName) {
		// TODO Auto-generated method stub
		try {
			Product product = findById(productName);
			if (product != null) {
				setTotalProductOfCategory(product, product.getCategory().getTotalProduct() - 1);
				repository.delete(product);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean update(Product product) {
		// TODO Auto-generated method stub
		try {
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product findById(String productName) {
		// TODO Auto-generated method stub
		Optional<Product> opt = repository.findById(productName);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	private void setTotalProductOfCategory(Product product, int totalProduct) {
		Category category = product.getCategory();
		category.setTotalProduct(totalProduct);
	}

	@Override
	@Transactional
	public boolean updateComment(Product product, Comment comment) {
		// TODO Auto-generated method stub
		try {
			product = findById(product.getName());
			List<Comment> comments = product.getComments();
			comments.add(comment);
			product.setComments(comments);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEvent(Product product, Event event) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			product = findById(product.getName());
			product.setEvent(event);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateCoupon(Product product, Coupon coupon) {
		// TODO Auto-generated method stub
		try {
			product.setCoupon(coupon);
			repository.saveAndFlush(product);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void insertAddtionInformation(Product product, String categoryId, String discount, String endAt) {
		ProductDetail productDetail = new ProductDetail(product.getName(), null, "Còn hàng", product);
		product.setProductDetail(productDetail);
		if (categoryId != null && !categoryId.isBlank()) {
			Category category = categoryService.addProductToList(categoryId, product);
			if (category == null) {
				category = new Category(categoryId, null);
				category.addProduct(product);
			}
		}
		if (discount != null && !discount.isBlank()) {
			Date date = null;
			if (endAt != null && !endAt.isBlank()) {
				java.util.Date dateUtil = new java.util.Date(endAt);
				date = new Date(dateUtil.getTime());
			}
			Coupon coupon = new Coupon(product.getName() + "-" + System.currentTimeMillis(), (short) 10, date);
			product.addCoupon(coupon);
		}

	}

	@Override
	@Transactional
	public void addMultipleProductFromFile(Workbook workbook, IngredientService ingredientService,
			SupplierService supplierService) {
		// TODO Auto-generated method stub
		Map<String, Product> map = new LinkedHashMap<String, Product>();
		Map<String, Supplier> mapSupplier = new LinkedHashMap<String, Supplier>();
		Map<String, Ingredient> mapIngredient = new LinkedHashMap<String, Ingredient>();
		Sheet sheet = workbook.getSheetAt(0);
		boolean isHeader = true;
		Product product = null;
		Supplier supplier = null;
		for (Row row : sheet) {
			if (isHeader) {
				isHeader = false;
				continue;
			}
			String name = getValue(sheet, row.getRowNum(), 0);
			String image = getValue(sheet, row.getRowNum(), 1);
			String size = getCellValue(row.getCell(2));
			String price = getCellValue(row.getCell(3));
			String ingredients = getCellValue(row.getCell(4));
			String ingredientsPrice = getCellValue(row.getCell(5));
			String quantity = getCellValue(row.getCell(6));
			String supplierId = getCellValue(row.getCell(7));
			String supplierAddress = getCellValue(row.getCell(8));
			String supplierPhone = getCellValue(row.getCell(9));
			String categoryId = getValue(sheet, row.getRowNum(), 10);
			String discount = getValue(sheet, row.getRowNum(), 11);
			String endDate = getValue(sheet, row.getRowNum(), 12);
			product = findById(name);
			if (name != null && !map.containsKey(name) && product == null) {
				product = new Product(name, image);
				insertAddtionInformation(product, categoryId, discount, endDate);
			}
			if (name != null && size != null && !size.isBlank() && !product.getSizes().contains(size)) {
				product.getSizes().add(size);
			}
			if (name != null && price != null && !price.isBlank()) {
				int p = (price.isBlank()) ? 0 : Integer.parseInt(price);
				if (!product.getPrices().contains(p)) {
					product.getPrices().add(p);
				}
			}
			if (name != null && ingredients != null && !ingredients.isBlank()) {
				Ingredient i = mapIngredient.get(ingredients);
				if (i == null) {
					i = ingredientService.findById(ingredients);
					if (i == null) {// Khi nguyên liệu chưa tồn tại trong DB
						i = new Ingredient(ingredients, quantity.toLowerCase(), Long.parseLong(ingredientsPrice));
					}
					mapIngredient.put(ingredients, i);
					supplier = mapSupplier.get(supplierId);
					if (supplier == null) { // Chưa tồn tại trong map
						supplier = i.getSupplier();
						if (supplier == null) { // Sản phẩm chưa có nhà cung cấp
							supplier = supplierService.findById(supplierId);
							if (supplier == null) { // Chưa tồn tại trong DB
								supplier = new Supplier(supplierId, supplierAddress, supplierPhone); // Tạo
																										// mới
																										// hoàn
																										// toàn
								supplier.addIngredient(i);
							} else {
								if (!supplier.alreadyContainIngredient(i.getName()))
									supplier.addIngredient(i);
							}
						}
						mapSupplier.put(supplierId, supplier);

					} else {// Đã tồn tại trong map
						if (!supplier.alreadyContainIngredient(i.getName())) {
							mapSupplier.get(supplierId).addIngredient(i);
						}
					}

				}
				product.addIngredient(i);
			}
			map.put(name, product);
		}
//		map.values().stream().filter(o -> !o.getName().isBlank()).forEach(System.out::println);
//		repository.saveAllAndFlush(
//				new ArrayList<Product>(map.values()).stream().filter(o -> !o.getName().isBlank()).toList());
		Collection<Product> products = map.values();
		for (Product product2 : products) {
			if (product2.getName() != null && !product2.getName().isBlank()) {
				repository.save(product2);
				repository.flush();
			}

		}
	}

	private String getValue(Sheet sheet, int row, int col) {
		/*-
		 * Duyệt qua tất cả các ô merge trong sheet 
		 * kiểm tra địa chỉ (hàng, cột) của ô merge đó có cùng với row, col không
		 */
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);
			if (region.isInRange(row, col)) {
				Row firstRow = sheet.getRow(region.getFirstRow());
				Cell cell = firstRow.getCell(region.getFirstColumn());
				return getCellValue(cell);
			}
		}
		// Trường hợp hàng có ô không merge thì lấy giá trị trực tiếp từ ô đó
		Row firstRow = sheet.getRow(row);
		Cell cell = firstRow.getCell(col);
		return getCellValue(cell);
	}

	private String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.format(cell.getDateCellValue());
			}
			int integerValue = (int) cell.getNumericCellValue();
			return String.valueOf(integerValue).trim();
		}
		if (cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue().trim();

		return "";
	}

}
