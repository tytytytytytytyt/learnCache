/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.geotmt.cacheprime.zookeeper.distributed;

import lombok.extern.log4j.Log4j2;



public class OrderService implements Runnable {

	private OrderNumGenerator generator = new OrderNumGenerator();
	private ZkDistributeLock lock = new ZkDistributeLock();


	@Override
	public void run() {
		lock.getLock();
		System.out.println("threadName ："+Thread.currentThread().getName()+" seq :"+ generator.getNumber());
		lock.unLock();
	}


	public static void main(String[] args) {
		for (int i = 0; i < 100 ; i++) {
			new Thread(new OrderService()).start();
		}
	}
}
