package com.gurundong.threadproject.thread.threadtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class TestImport {
	private ArrayBlockingQueue<String> l = new ArrayBlockingQueue<>(200);
	private int fileEnd = 0;

	//mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://101.132.36.26:3306/inspur_bss_new_new";
    //用户名
    private static final String USER_NAME = "grd";
    //密码
    private static final String PASSWORD = "asdf1234";
    //数据库连接对象
    private static Connection connection;

	public static void main(String[] args) {
		TestImport testImport = new TestImport();
		testImport.MySQLHandle();
		String file = "C:\\Users\\zjxgrd\\Desktop\\account_bill.sql";
		// String file = "E:\\grd\\inspur\\111.txt";

		try {
			Thread t = new Thread() {
				@Override
				public void run() {
					System.out.println("thread 1");
					try {
						Thread.sleep(1000);
						
						// 消费数据
						List<String> readyList = null;
						while(true){
							if(testImport.l.isEmpty()){
								break;
							}
							if(readyList == null){
								readyList = new ArrayList<>();
								readyList.add(testImport.l.poll());
							}
							else {
								if(readyList.size() < 200){
									readyList.add(testImport.l.poll());
								}else{
									testImport.batchSql(readyList);
									readyList = null;
								}
								
							}
							System.out.println("POP" + testImport.l.poll());
						}
						testImport.batchSql(readyList);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			};
			t.start();
			
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String value;
//			StringBuilder sb = new StringBuilder();
			long num = 0L;
			// System.out.println("共计："+br.lines().count()+"行数据");
			value = br.readLine();
			System.out.println(value);
			value = br.readLine();
			System.out.println(value);
			value = br.readLine();
			System.out.println(value);
			while((value = br.readLine()) != null) {
				System.out.println("读取第:"+num+"行");
				System.out.println(testImport.l.size());
				System.out.println(value);
				testImport.l.put(value);
				num++;
			}
			testImport.fileEnd = 1;
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	 //构造函数，包括连接数据库等操作
	 public void MySQLHandle(){
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
	}
	
	//带参数构造函数，用于指定参数连接数据库
    public void MySQLHandle(String Database,String User,String Password){
        try {
            //获取数据库连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Database, User, Password);
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
    }
 
    public Connection getConnection(){
        return connection;
    }
 
    public static void ReleaseConnect(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	 	/**
		 * @方法描述：批量执行SQL语句
		 * @param readyList 包含待执行的SQL语句的ArrayList集合
		 * @return int 影响的函数
		 */
		public int batchSql(List<String> readyList) {
			try {
				Statement st = connection.createStatement();
				for (String subsql : readyList) {
                st.addBatch(subsql);
            }
            st.executeBatch();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}
