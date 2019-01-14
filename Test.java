import java.util.ArrayList;
import java.util.Scanner;

public class Test{
	static Scanner scan = null;
	static DbUtil dbUtil = null;
	public static void main(String []args) {
		scan = new Scanner(System.in);
		dbUtil = new DbUtil();
		int flag = 1;	//flag=0.退出程序，1.未登录菜单，2.用户菜单，3.管理员菜单
		while(flag != 0 ) {
			switch(flag) {
			case 1 :
				flag = unlogMenu();break;
			case 2:
				flag = userMenu();break;
			case 3:
				flag = adminMenu();break;
			default:
				System.out.println("功能结束的意外返回值");
				flag = 0;
				break;
			}
		}
	}
	
	static int unlogMenu() {
		System.out.println("-----图书管理系统(未登录）-----");
		System.out.println("1.登录");
		System.out.println("2.查书");
		System.out.println("3.显示图书借阅排行");
		System.out.println("0.退出");
		System.out.print("请输入功能号：");

		char ch = scan.next().charAt(0);
		System.out.println("");
		
		switch(ch) {
			case '1':
				return __login();
			case '2':
				__search();
				break;
			case '3':
				System.out.println("---图书借阅排行榜---");
				dbUtil.showBooksOrdeByBorrowNum();
				break;
			case '0':
				return 0;
			default:
				System.out.println("请输入合法的字符");
				break;
			}
		return 1;	//如果未切换用户，则继续未登录循环
	}
		
	static int userMenu() {
		System.out.println("-----图书管理系统-----");
		System.out.println("1.退出登录");
		System.out.println("2.借书");
		System.out.println("3.还书");
		System.out.println("4.查书");
		System.out.println("5.显示历史借阅");
		System.out.println("6.显示当前已借");
		System.out.println("7.显示图书借阅排行");
		System.out.println("0.退出系统");
		System.out.print("请输入功能号：");
		
		char ch = scan.next().charAt(0);
		System.out.println("");
		
		switch(ch) {
			case '1':
				return 1;	//转未登录界面
			case '2':
				System.out.print("书籍编号：");
				String borrowcode = scan.next();
				dbUtil.borrowBook(borrowcode);
				System.out.println("\r\n借书成功\r\n");
				break;
			case '3':
				System.out.print("书籍编号：");
				String returncode = scan.next();
				dbUtil.returnBook(returncode);
				System.out.println("\r\n还书成功\r\n");
				break;
			case '4':
				System.out.println("---图书搜索---");
				__search();
				break;
			case '5':
				System.out.println("---历史借阅---");
				showBooks(dbUtil.showHistoryBorrowedBooks());
				break;
			case '6':
				System.out.println("---当前已借---");
				showBooks(dbUtil.showCurrentBorrowedBooks());
				break;
			case '7':
				System.out.println("---图书借阅排行榜---");
				dbUtil.showBooksOrdeByBorrowNum();
				break;
			case '0':
				System.out.println("退出系统成功\r\n");
				return 0;
			default:
				System.out.println("请输入合法的字符\r\n");
				break;
		}
		return 2;
	}
		
	static int adminMenu() {
		System.out.println("-----图书管理系统(管理员)-----");
		System.out.println("1.退出登录");
		System.out.println("2.添加新书");
		System.out.println("3.添加书类");
		System.out.println("0.退出系统");
		System.out.print("请输入功能号：");
		
		char ch = scan.next().charAt(0);
		System.out.println("");
		
		switch(ch) {
			case '1':
				return 1;	//转未登录界面
			case '2':
				__addBook();
				break;
			case '3':
				__addBooks();
				break;
			case '0':
				System.out.println("退出系统成功\r\n");
				return 0;
			default:
				System.out.println("请输入合法的字符\r\n");
				break;
		}
		return 3;
	}
		
	static int __login(){
		System.out.print("用户名：");
		String username = scan.next();
		System.out.print("密码：");
		String password = scan.next();
		int flag = dbUtil.login(username,password);
		if(flag ==1 || flag ==2){
			System.out.println("登录成功\r\n");
			System.out.println(flag);
			return ++flag;
		}
		else if(flag == -1){
			System.out.println("用户名不正确\r\n");
		}
		else if(flag == 0){
			System.out.println("用户名和密码不匹配\r\n");
		}
		return 1;
	}
		
	static void __search(){
		System.out.print("书籍名称：");
		String bookname = scan.next();
		String bookcode = dbUtil.searchByName(bookname);
		if(bookcode != null)
			System.out.println("该书可借，编号为："+bookcode + "\r\n");
		else
			System.out.println("该书不可借\r\n");
	}
	
	static void __addBook() {
		System.out.println("---添加新书---");
		Book book = new Book();
		System.out.print("书籍编号：");
		book.barcode = scan.next();
		System.out.print("检索号：");
		book.searchno = scan.next();
		System.out.print("位置：");
		book.position = scan.next();
		System.out.print("是否可借：");
		book.state = scan.next();
		dbUtil.addBook(book);
		System.out.println("\r\n添加成功\r\n");
	}
	
	static void __addBooks() {
		System.out.println("---添加书类---");
		Book book = new Book();
		System.out.print("索引号：");
		book.searchno = scan.next();
		System.out.print("isbn号：");
		book.isbn = scan.next();
		System.out.print("书名：");
		book.bookName = scan.next();
		System.out.print("出版方：");
		book.publisher = scan.next();
		System.out.print("作者：");
		book.author = scan.next();
		System.out.print("类型：");
		book.type = scan.next();
		System.out.print("价格：");
		book.price = scan.nextInt();
		dbUtil.addBooks(book);
		System.out.println("\r\n添加成功\r\n");
	}
	
	//按表格形式显示书籍列表
	static void showBooks(ArrayList<Book> books) {
		System.out.println("编号\t书名\t作者\t出版社\t借出\t位置");
		for(Book book:books)
			System.out.println(book.barcode +"\t"+ book.bookName +"\t"+ book.author +"\t"+ book.publisher +"\t"+ book.isbn +"\t"+ book.position);
		System.out.println("\r\n");
	}
	
}
	

	
