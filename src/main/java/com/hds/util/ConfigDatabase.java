package com.hds.util;

import com.hds.model.AddressPojo;
import com.hds.model.CustomerPojo;
import com.hds.model.EmployeePojo;
import com.hds.model.ProductPojo;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ConfigDatabase
{
//________________________________
//	Customer Section
//________________________________

//	//Get the next primary key in the address table
	public int getNextAddressId()
	{
		Transaction transaction = null;
		int addressID = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "select max(address.AddressID) from hds.address";
			List IDResult = session.createSQLQuery(queryString).list();
			addressID = Integer.parseInt(IDResult.get(0).toString()) + 1;

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return addressID;
	}

	//Get the next primary key in the customer table
	public int getNextCustomerId()
	{
		Transaction transaction = null;
		int customerID = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "select max(CustomerID) from hds.customer";
			List customerIDResult = session.createSQLQuery(queryString).list();
			customerID = Integer.parseInt(customerIDResult.get(0).toString()) + 1;

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return customerID;
	}

	//View the WHOLE customer table
	public List customerViewDB()
	{
		Transaction transaction = null;
		List customerList = new ArrayList<CustomerPojo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "SELECT \n" +
					"    c.CustomerID,\n" +
					"    c.LastName,\n" +
					"    c.FirstName,\n" +
					"    c.MI,\n" +
					"    c.PhoneNum,\n" +
					"    c.Email,\n" +
					"    c.OrderCount,\n" +
					"    c.AccountBalance,\n" +
					"    a.Street,\n" +
					"    a.City,\n" +
					"    a.State,\n" +
					"    a.Zip,\n" +
					"    a.AddressID\n" +
					"FROM\n" +
					"    hds.customer c\n" +
					"        INNER JOIN\n" +
					"    hds.address a ON c.AddressID = a.AddressID";
			SQLQuery query = session.createSQLQuery(queryString);

			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				CustomerPojo customer = new CustomerPojo();
				customer.setCustomer_id(Integer.parseInt(row[0].toString()));
				// skip row[1] since we don't need to display addressID
				customer.setCustomer_last_name(row[1].toString());
				customer.setCustomer_first_name(row[2].toString());
				customer.setCustomer_mi(row[3].toString());
				if(row[4] != null)
					customer.setCustomer_phone_num(row[4].toString());
				if(row[5] != null)
					customer.setCustomer_email(row[5].toString());
				customer.setCustomer_order_count(Integer.parseInt(row[6].toString()));
				customer.setCustomer_account_balance(Double.parseDouble(row[7].toString()));
				customer.setStreet(row[8].toString());
				customer.setCity(row[9].toString());
				customer.setState(row[10].toString());
				customer.setZip(Integer.parseInt(row[11].toString()));
				customerList.add(customer);
			}

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return customerList;
	}

	//Query the Customer and Address database where Customer Id = customerId
	public List editCustomer(int customerId)
	{
		Transaction transaction = null;
		List customerList = new ArrayList<CustomerPojo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "SELECT \n" +
					"    c.CustomerID,\n" +
					"    c.LastName,\n" +
					"    c.FirstName,\n" +
					"    c.MI,\n" +
					"    c.PhoneNum,\n" +
					"    c.Email,\n" +
					"    c.OrderCount,\n" +
					"    c.AccountBalance,\n" +
					"    a.Street,\n" +
					"    a.City,\n" +
					"    a.State,\n" +
					"    a.Zip,\n" +
					"    a.AddressID\n" +
					"FROM\n" +
					"    hds.customer c\n" +
					"        INNER JOIN\n" +
					"    hds.address a ON c.AddressID = a.AddressID\n" +
					"    where CustomerID =" + customerId;

			SQLQuery query = session.createSQLQuery(queryString);

			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				CustomerPojo customer = new CustomerPojo();
				customer.setCustomer_id(Integer.parseInt(row[0].toString()));
				// skip row[1] since we don't need to display addressID
				customer.setCustomer_last_name(row[1].toString());
				customer.setCustomer_first_name(row[2].toString());
				customer.setCustomer_mi(row[3].toString());
				if(row[4] != null)
					customer.setCustomer_phone_num(row[4].toString());
				if(row[5] != null)
					customer.setCustomer_email(row[5].toString());
				customer.setCustomer_order_count(Integer.parseInt(row[6].toString()));
				customer.setCustomer_account_balance(Double.parseDouble(row[7].toString()));
				customer.setStreet(row[8].toString());
				customer.setCity(row[9].toString());
				customer.setState(row[10].toString());
				customer.setZip(Integer.parseInt(row[11].toString()));
				customer.setCustomer_address_id(Integer.parseInt(row[12].toString()));
				;
				customerList.add(customer);
			}

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return customerList;

	}

	//Add Object to their matching database
	public void addToDataBase(Object object)
	{
		System.out.println("Adding " + object.toString());
		Transaction transaction = null;
		try (
				Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			session.save(object);

			transaction.commit();

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Update Object in their corresponding table
	public void updateDatabase(Object object)
	{
		System.out.println("Updating " + object.toString());
		Transaction transaction = null;
		try (
				Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			session.update(object);

			transaction.commit();

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Delete customer with matching ID from customer table
	public void deleteCustomer(int customerId)
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			CustomerPojo customerPojo = session.get(CustomerPojo.class, customerId);
			if(customerPojo != null)
			{
				session.delete(customerPojo);
			}
			transaction.commit();
		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	////Delete address with matching ID from address table
	public void deleteAddress(int idToDoList)
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			AddressPojo addressPojo = session.get(AddressPojo.class, idToDoList);
			if(addressPojo != null)
			{
				session.delete(addressPojo);
			}

			transaction.commit();
		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	//________________________________
	//	Employee Section
	//________________________________

	public List employeeViewDB()
	{
		Transaction transaction = null;
		List employeeList = new ArrayList<EmployeePojo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "SELECT \n" +
					"    e.EmployeeID,\n" +
					"    e.LastName,\n" +
					"    e.FirstName,\n" +
					"    e.MI,\n" +
					"    jp.JobTitle,\n" +
					"    job.YearlySalary,\n" +
					"    o.Name as officeLocation,\n" +
					"    esite.SiteUserID,\n" +
					"    a.Street,\n" +
					"    a.City,\n" +
					"    a.State,\n" +
					"    a.Zip,\n" +
					"    es.IsActive\n" +
					"FROM\n" +
					"    hds.employee e\n" +
					"join hds.address a on e.AddressID = a.AddressID\n" +
					"join hds.employeestatus es on e.StatusID = es.StatusID\n" +
					"join hds.officelocation o on e.OfficeLocationID = o.LocationID \n" +
					"join hds.employeesiteuser esite on e.SiteUserID = esite.SiteUserID\n" +
					"join hds.jobposition jp on e.JobID = jp.JobPositionID\n" +
					"join hds.job on e.JobID = job.JobID;";
			SQLQuery query = session.createSQLQuery(queryString);

			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				EmployeePojo employee = new EmployeePojo();
				employee.setEmployee_id(Integer.parseInt(row[0].toString()));
				employee.setLast_name(row[1].toString());
				employee.setFirst_name(row[2].toString());
				employee.setMi(row[3].toString());
				employee.setPosition(row[4].toString());
				if(row[5] != null)
					employee.setPayRate(Integer.parseInt(row[5].toString()));
				employee.setOfficeLocation(row[6].toString());
				employee.setSite_user_id(Integer.parseInt(row[7].toString()));
				employee.setStreet(row[8].toString());
				employee.setCity(row[9].toString());
				employee.setState(row[10].toString());
				employee.setZip(Integer.parseInt(row[11].toString()));
				employee.setStatus(row[12].toString());
				employeeList.add(employee);
			}

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return employeeList;
	}

	public List editEmployee(int employee_id)
	{
		Transaction transaction = null;
		List employeeList = new ArrayList<EmployeePojo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();
			String queryString = "SELECT \n" +
					"    e.EmployeeID,\n" +
					"    e.LastName,\n" +
					"    e.FirstName,\n" +
					"    e.MI,\n" +
					"    jp.JobTitle,\n" +
					"    job.YearlySalary,\n" +
					"    o.Name as officeLocation,\n" +
					"    esite.SiteUserID,\n" +
					"    a.Street,\n" +
					"    a.City,\n" +
					"    a.State,\n" +
					"    a.Zip,\n" +
					"    es.IsActive\n" +
					"    a.AddressID\n" +
					"FROM\n" +
					"    hds.employee e\n" +
					"join hds.address a on e.AddressID = a.AddressID\n" +
					"join hds.employeestatus es on e.StatusID = es.StatusID\n" +
					"join hds.officelocation o on e.OfficeLocationID = o.LocationID \n" +
					"join hds.employeesiteuser esite on e.SiteUserID = esite.SiteUserID\n" +
					"join hds.jobposition jp on e.JobID = jp.JobPositionID\n" +
					"join hds.job on e.JobID = job.JobID;"+
					"    where EmployeeID =" + employee_id;
			SQLQuery query = session.createSQLQuery(queryString);

			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				EmployeePojo employee = new EmployeePojo();
				employee.setEmployee_id(Integer.parseInt(row[0].toString()));
				employee.setLast_name(row[1].toString());
				employee.setFirst_name(row[2].toString());
				employee.setMi(row[3].toString());
				employee.setPosition(row[4].toString());
				if(row[5] != null)
					employee.setPayRate(Integer.parseInt(row[5].toString()));
				employee.setOfficeLocation(row[6].toString());
				employee.setSite_user_id(Integer.parseInt(row[7].toString()));
				employee.setStreet(row[8].toString());
				employee.setCity(row[9].toString());
				employee.setState(row[10].toString());
				employee.setZip(Integer.parseInt(row[11].toString()));
				employee.setStatus(row[12].toString());
				employee.setAddress_id(Integer.parseInt(row[13].toString()));
				employeeList.add(employee);
			}

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return employeeList;

	}

	//________________________________
	//	Inventory Section
	//________________________________
	public List inventoryViewDB()
	{
		Transaction transaction = null;
		List productList = new ArrayList<ProductPojo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			session.beginTransaction();

			SQLQuery query = session.createSQLQuery("SELECT \n" +
					"    product.ProductID,\n" +
					"    productbrand.ProductName,\n" +
					"    product.CategoryID,\n" +
					"    product.InventoryCount,\n" +
					"    product.ModelNum,\n" +
					"    product.SerialNum,\n" +
					"    product.Description,\n" +
					"    product.Cost,\n" +
					"    product.ListPrice,\n" +
					"    product.DeliveryCost,\n" +
					"    product.IsActive\n" +
					"FROM\n" +
					"    hds.product \n" +
					"    join hds.productbrand\n" +
					"\t\ton product.BrandID = productbrand.BrandID;  ");
			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				ProductPojo product = new ProductPojo();
				product.setProduct_id(Integer.parseInt(row[0].toString()));
				product.setBrandName(row[1].toString());
				if(row[2] != null)
					product.setCategoryName(row[2].toString());
				if(row[3] != null)
					product.setInventory_count(Integer.parseInt(row[3].toString()));
				product.setModel_num(row[4].toString());
				product.setSerial_num(Integer.parseInt(row[5].toString()));
				product.setDescription(row[6].toString());
				product.setCost(Double.parseDouble(row[7].toString()));
				product.setList_price(Double.parseDouble(row[8].toString()));
				if(row[9] != null)
					product.setDeliveryCost(Integer.parseInt(row[9].toString()));
				product.setIs_active(row[10].toString());
				productList.add(product);
			}

		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return productList;
	}

}

