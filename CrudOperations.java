package in.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CrudOperations {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			Connection connection = DriverManager.getConnection
			("jdbc:oracle:thin:@localhost:1521:orcl", "vikram","Pass!123");

			PreparedStatement insertStatement = 
				connection.prepareStatement("INSERT INTO Product VALUES(?,?,?,?)");

			PreparedStatement selectAllStatement = 
				connection.prepareStatement("SELECT * FROM Product");

			PreparedStatement selectByCodeStatement =
				connection.prepareStatement("SELECT * FROM Product WHERE code=?");

			PreparedStatement updateStatement =
				connection.prepareStatement("UPDATE Product SET price=?, qty=qty+? WHERE code=?");

			PreparedStatement deleteStatement = 
				connection.prepareStatement("DELETE FROM Product WHERE code=?");

			while (true) {
				System.out.println("===== Choice =====");
				System.out.println(" 1. Add Product\n" + " 2. View All Products\n" + " 3. View Product By Code\n"
						+ " 4. Update Product By Code (price/qty)\n" + " 5. Delete Product By Code\n" + " 6. Exit");
				System.out.println("Enter the Choice:");

				switch (Integer.parseInt(scanner.nextLine())) {
				case 1:
					addProduct(scanner, insertStatement);
					break;
				case 2:
					viewAllProducts(selectAllStatement);
					break;
				case 3:
					viewProductByCode(scanner, selectByCodeStatement);
					break;
				case 4:
					updateProductByCode(scanner, updateStatement);
					break;
				case 5:
					deleteProductByCode(scanner, deleteStatement);
					break;
				case 6:
					System.out.println("Operations on Product Table Stopped");
					connection.close();
					System.exit(0);
				default:
					System.out.println("Invalid Choice...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addProduct(Scanner scanner, PreparedStatement insertStatement) throws Exception {
		System.out.println("Enter the Code:");
		String code = scanner.nextLine();
		System.out.println("Enter the Name:");
		String name = scanner.nextLine();
		System.out.println("Enter the Price:");
		float price = Float.parseFloat(scanner.nextLine());
		System.out.println("Enter the Qty:");
		int qty = Integer.parseInt(scanner.nextLine());

		insertStatement.setString(1, code);
		insertStatement.setString(2, name);
		insertStatement.setFloat(3, price);
		insertStatement.setInt(4, qty);

		int k = insertStatement.executeUpdate();
		if (k > 0) {
			System.out.println("Product inserted Successfully...");
		}
	}

	private static void viewAllProducts(PreparedStatement selectAllStatement) throws Exception {
		ResultSet rs = selectAllStatement.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getFloat(3) + "\t" + rs.getInt(4));
		}
	}

	private static void viewProductByCode(Scanner scanner, PreparedStatement selectByCodeStatement) throws Exception {
		System.out.println("Enter the ProdCode:");
		String cd = scanner.nextLine();
		selectByCodeStatement.setString(1, cd);

		ResultSet rs2 = selectByCodeStatement.executeQuery();
		if (rs2.next()) {
			System.out.println(
					rs2.getString(1) + "\t" + rs2.getString(2) + "\t" + rs2.getFloat(3) + "\t" + rs2.getInt(4));
		} else {
			System.out.println("Invalid ProdCode...");
		}
	}

	private static void updateProductByCode(Scanner scanner, PreparedStatement updateStatement) throws Exception {
		System.out.println("Enter the ProdCode:");
		String cd2 = scanner.nextLine();
		updateStatement.setString(1, cd2);

		ResultSet rs3 = updateStatement.executeQuery();
		if (rs3.next()) {
			System.out.println("Old price: " + rs3.getFloat(3));
			System.out.println("Enter the New Price:");
			float nPrice = Float.parseFloat(scanner.nextLine());
			System.out.println("Available qty: " + rs3.getInt(4));
			System.out.println("Enter qty:");
			int nQty = Integer.parseInt(scanner.nextLine());

			updateStatement.setFloat(1, nPrice);
			updateStatement.setInt(2, nQty);
			updateStatement.setString(3, cd2);
			int z = updateStatement.executeUpdate();
			if (z > 0) {
				System.out.println("Product details Updated...");
			}
		} else {
			System.out.println("Invalid ProdCode...");
		}
	}

	private static void deleteProductByCode(Scanner scanner, PreparedStatement deleteStatement) throws Exception {
		System.out.println("Enter the ProdCode:");
		String cd3 = scanner.nextLine();
		deleteStatement.setString(1, cd3);

		ResultSet rs4 = deleteStatement.executeQuery();
		if (rs4.next()) {
			int y = deleteStatement.executeUpdate();
			if (y > 0) {
				System.out.println("Product details deleted successfully...");
			}
		} else {
			System.out.println("Invalid Product Code...");
		}
	}
}

