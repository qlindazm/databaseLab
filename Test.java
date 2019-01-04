import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	static Scanner scan = null;
	public static void main(String []args) {
		scan = new Scanner(System.in);
		DbUtil dbUtil = new DbUtil();
		boolean isLoop = true;
		for(isLoop = true; isLoop; isLoop = preLogin(dbUtil)）{};
		for(isLoop = true; isLoop; isLoop = menu(dbUtil)）{};
		/*while(isLoop)
			isLoop = preLogin(dbUtil);
		isLoop = true;
		while(isLoop) 
			isLoop = menu(dbUtil);*/
		scan.close();
	}
	
	static boolean preLogin(DbUtil dbUtil){
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
				return false;
			default:
				System.out.println("请输入合法的字符");
				break;
			}
		}
		return true;
	}

	static boolean menu(DbUtil dbUtil){
		System.out.println("-----图书管理系统-----");
		System.out.println("1.切换用户");
		System.out.println("2.借书");
		System.out.println("3.还书");
		System.out.println("4.查书");
		System.out.println("5.显示历史借阅");
		System.out.println("6.显示当前已借");
		System.out.println("7.显示图书借阅排行");
		System.out.println("0.退出");
		System.out.print("请输入功能号：");
		
		char ch = scan.next().charAt(0);
		System.out.println("");
		
		switch(ch) {
			case '1':
				__login();
				break;
			case '2':
				System.out.print("书籍编号：");
				String borrowcode = scan.next();
				dbUtil.borrowBook(borrowcode);
				System.out.println("\r\n借书成功");
				break;
			case '3':
				System.out.print("书籍编号：");
				String returncode = scan.next();
				dbUtil.returnBook(returncode);
				System.out.println("\r\n还书成功");
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
				return false;
			default:
				System.out.println("请输入合法的字符");
				break;
		}
		return true;
	}

	static boolean __login(){
				System.out.print("用户名：");
				String username = scan.next();
				System.out.print("密码：");
				String password = scan.next();
				int flag = dbUtil.login(username,password);
				if(flag ==1){
					System.out.println("登录成功");
					return true;
				}
				els if(flag == 0){
					System.out.println("用户名不正确");
				}
				else{
					System.out.println("用户名和密码不匹配");
				}
				return false;
	}

	static void __search(){
		System.out.print("书籍名称：");
		String bookname = scan.next();
		String bookcode = dbUtil.searchByName(bookname);
		if(bookcode != null)
			System.out.println("该书可借，编号为："+bookcode);
		else
			System.out.println("该书不可借");
	}


	
	//按表格形式显示书籍列表
	static void showBooks(ArrayList<Book> books) {
		System.out.println("编号\t书名\t作者\t出版社\t借出\t位置");
		for(Book book:books)
			System.out.println(book.barcode +"\t"+ book.bookName +"\t"+ book.author +"\t"+ book.publisher +"\t"+ book.isbn +"\t"+ book.position);
	}
	
}
