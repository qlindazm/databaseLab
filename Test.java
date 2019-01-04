import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	static Scanner scan = null;
	public static void main(String []args) {
		scan = new Scanner(System.in);
		DbUtil dbUtil = new DbUtil();
		int isPreMeun;		//1循环，0退出，-1登录成功
		for(isPreMeun = 1; isPreMeun == 1; isPreMeun = preMeun(dbUtil)){}
		
		
		if(isPreMeun != 0)
			for(boolean isMainMeun = true; isMainMeun; isMainMeun = mainMenu(dbUtil)) {}
		/*while(isLoop)
			isLoop = preLogin(dbUtil);
		isLoop = true;
		while(isLoop) 
			isLoop = menu(dbUtil);*/
		scan.close();
	}
	
	static int preMeun(DbUtil dbUtil){
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
				return __login(dbUtil);
			case '2':
				__search(dbUtil);
				tail();
				break;
			case '3':
				System.out.println("---图书借阅排行榜---");
				dbUtil.showBooksOrdeByBorrowNum();
				tail();
				break;
			case '0':
				return 0;
			default:
				System.out.println("请输入合法的字符");
				tail();
				break;
		}
		return 1;
	}

	static boolean mainMenu(DbUtil dbUtil){
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
				__login(dbUtil);
				tail();
				break;
			case '2':
				System.out.print("书籍编号：");
				String borrowcode = scan.next();
				dbUtil.borrowBook(borrowcode);
				System.out.println("\r\n借书成功");
				tail();
				break;
			case '3':
				System.out.print("书籍编号：");
				String returncode = scan.next();
				dbUtil.returnBook(returncode);
				System.out.println("\r\n还书成功");
				tail();
				break;
			case '4':
				System.out.println("---图书搜索---");
				__search(dbUtil);
				tail();
				break;
			case '5':
				System.out.println("---历史借阅---");
				showBooks(dbUtil.showHistoryBorrowedBooks());
				tail();
				break;
			case '6':
				System.out.println("---当前已借---");
				showBooks(dbUtil.showCurrentBorrowedBooks());
				tail();
				break;
			case '7':
				System.out.println("---图书借阅排行榜---");
				dbUtil.showBooksOrdeByBorrowNum();
				tail();
				break;
			case '0':
				return false;
			default:
				System.out.println("请输入合法的字符");
				tail();
				break;
		}
		return true;
	}

	static int __login(DbUtil dbUtil){
				System.out.print("用户名：");
				String username = scan.next();
				System.out.print("密码：");
				String password = scan.next();
				int flag = dbUtil.login(username,password);
				if(flag ==1){
					System.out.println("\r\n登录成功\r\n");
					return -1;
				}
				else if(flag == 0){
					System.out.println("用户名不正确");
				}
				else{
					System.out.println("用户名和密码不匹配");
				}
				return 1;
	}

	static void __search(DbUtil dbUtil){
		System.out.print("书籍名称：");
		String bookname = scan.next();
		String bookcode = dbUtil.searchByName(bookname);
		if(bookcode != null)
			System.out.println("该书可借，编号为："+bookcode);
		else
			System.out.println("该书不可借");
	}

	static void tail(){
		System.out.println("\r\n--------------------\r\n按回车键返回菜单");
		try{System.in.read();}
		catch(Exception e) {e.printStackTrace();}
	}
	
	//按表格形式显示书籍列表
	static void showBooks(ArrayList<Book> books) {
		System.out.println("编号\t\t书名\t\t作者\t出版社\t借出\t位置");
		for(Book book:books)
			System.out.println(book.barcode +"\t"+ book.bookName +"\t"+ book.author +"\t"+ book.publisher +"\t"+ book.isbn +"\t"+ book.position);
	}
	
}