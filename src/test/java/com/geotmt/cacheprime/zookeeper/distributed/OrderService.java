/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.geotmt.cacheprime.zookeeper.distributed;

public class OrderService implements Runnable {
	private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();
	private ZkDistributeLock extLock = new ZkDistributeLock();

	public void run() {
		getNumber();
	}

	public void getNumber() {
		try {
			extLock.getLock();
			String number = orderNumGenerator.getNumber();
			System.out.println("线程:" + Thread.currentThread().getName() + ",生成订单id:" + number);
		} catch (Exception e) {

		} finally {
			extLock.unLock();
		}
	}

	public static void main(String[] args) {
		System.out.println("多线程生成number");
		// OrderService orderService = new OrderService();
		for (int i = 0; i < 100; i++) {
			new Thread(new OrderService()).start();
		}
	}

}
