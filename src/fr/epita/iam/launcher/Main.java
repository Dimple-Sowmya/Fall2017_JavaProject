/**
 *
 * Code application : Composant :
 */
package fr.epita.iam.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.datamodel.User;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeleteException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;
import fr.epita.iam.services.dao.IdentityJDBCDAO;
import fr.epita.iam.services.dao.LoginJdbdcDAO;

/**
 * <h3>Description</h3>
 * <p>
 * Cette classe permet de ...
 * </p>
 *
 * <h3>Utilisation</h3>
 * <p>
 * Elle s'utilise de la manière suivante :
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see Voir aussi $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class Main {
	static boolean counter=true;

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This method allows to launch the program
	 * </p>
	 *
	 * <h3>Utilisation</h3>
	 * <p>
	 * The first argument is the file path to the configuration file
	 * </p>
	 * <p>
	 *
	 * <pre>
	 * <code>
	 * java -Dconf=${configLocation} -jar ${jarName}
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 * @throws IdentitySearchException 
	 * @throws IdentityCreationException 
	 * @throws IdentityDeleteException 
	 * @throws IdentityUpdateException 
	 */
	public static void main(String[] args) throws IdentitySearchException, IdentityCreationException, IdentityDeleteException, IdentityUpdateException {
		
		
		

		
		LoginJdbdcDAO dbl = new LoginJdbdcDAO();
		IdentityJDBCDAO db = new IdentityJDBCDAO();
		Scanner sc = new Scanner(System.in);
		System.out.println("--------------Welcome to IAM Core----------------");
		System.out.println("please enter Username and Password");
		System.out.print("User Name : ");
		String uname = sc.next();
		System.out.print("Passwowrd : ");
		String pwd = sc.next();
		User us = new User();
		us.setName(uname);
		us.setPassword(pwd);
		List<User> users = dbl.search(us);
		User usr = new User();
		usr.setName("admin");
		usr.setPassword("admin");
		users.add(usr);
		
		
		for(User user : users)
		{
			
			

		
		if((user.getName().equals(uname)&&user.getPassword().equals(pwd))||(uname.equals("admin")&&pwd.equals("admin")))
		{
			System.out.println("--_-----Successfully Logged In----------_---");
			while(counter) {
			System.out.println("1. Create Identity");
			
			System.out.println("2. Search, Update & Delete Identity");
			System.out.println("3. Create User (for login purpose)");
			System.out.println("4. Logout or Exit");
			System.out.print("your choice : ");
			int ch = sc.nextInt();
			switch(ch)
			{
			case 1 :System.out.println("-----Create Identity------------");
					System.out.println("Please Enter Identiy");
					Identity id = new Identity();
					System.out.print("Name : ");
					String name = sc.next();
					id.setDisplayName(name);
					System.out.print("Email : ");
					String email = sc.next();
					id.setEmail(email);
					System.out.print("provide an ID : ");
					String id1 = sc.next();
					id.setUid(id1);
					db.create(id);
				break;
			
			case 2 :System.out.println("------------Search Identity------------");
					
				System.out.println("--Available Identitites :");
				System.out.println("----------------------------------------------------------------------------------------");
				
				System.out.println("");
				List<Identity> idens = db.searchAll();
				for (Identity ids : idens)
				{
					System.out.println("\t Id : "+ids.getUid()+" \t Name : "+ids.getDisplayName()+" \t Email : "+ids.getEmail());
					System.out.println("----------------------------------------------------------------------------------------");
				}
				boolean check = true;
				while(check)
				{
					System.out.println("1. Delete");
					System.out.println("2. Update");
					System.out.println("3. Go Back <-|");
					System.out.print("please enter your choice :");
					int chs = sc.nextInt();
					switch(chs)
					{
					case 1 : System.out.println("----------Delete Identity----------");
							System.out.println("For exit press \"E\" ");
							 System.out.println("please enter a UID from the above listed Identities :");
							 String uid = sc.next();
							 if(uid.equalsIgnoreCase("E"))
							 {
								 break;
							 }
							 else
							 {
								Identity ident = new Identity();
								ident.setUid(uid);
								db.delete(ident);
							 }
						break;
					case 2 :
						 System.out.println("----------Update Identity----------");
						// Identity identt = new Identity();
						 System.out.println("For exit press \"E\" ");
						 System.out.println("please enter a UID from the above listed Identities :");
						 String uidd = sc.next();
						 if(uidd.equalsIgnoreCase("E"))
						 {
							 break;
						 }
						 else
						 {
							Identity ident = new Identity();
							ident.setUid(uidd);
							System.out.println("please enter Identity :");
							System.out.println("Name : ");
							String uuname = sc.next();
							System.out.println("Email : ");
							String umail = sc.next();
							ident.setDisplayName(uuname);
							ident.setEmail(umail);
							db.update(ident);
						 }
						 break;
						
					
					case 3 :check=false;
					break;
					}
				}
				
				
				break;
			case 3 :
				System.out.println("------User Creation");
				System.out.println("please enter User ");
				System.out.print("Username : ");
				String unamee = sc.next();
				System.out.print("password : ");
				String pasd = sc.next();
				User u = new User();
				u.setName(unamee);
				u.setPassword(pasd);
				dbl.create(u);
				System.out.println("User Successfully Created.");

				break;
			case 4 : System.out.println("---------------Successfully Logged out. Thank you -----------------------------");
				counter=false;
				break;
			}
			}
		}
		
	}
		if(users.size()==0)
		{
			System.out.println("Sorry unable to find Login details.");
		}

}
}