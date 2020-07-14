package com.springboot.chapter15.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.chapter15.dao.ProductDao;
import com.springboot.chapter15.dao.PurchaseRecordDao;
import com.springboot.chapter15.pojo.ProductPo;
import com.springboot.chapter15.pojo.PurchaseRecordPo;
import com.springboot.chapter15.service.PurchaseService;

import redis.clients.jedis.Jedis;

/**** imports ****/
@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private ProductDao productDao = null;
	@Autowired
	private PurchaseRecordDao purchaseRecordDao = null;

	// @Override
	// // 启动Spring数据库事务机制
	// @Transactional
	// public boolean purchase(Long userId, Long productId, int quantity) {
	// // 获取产品
	// ProductPo product = productDao.getProduct(productId);
	// // 比较库存和购买数量
	// if (product.getStock() < quantity) {
	// // 库存不足
	// return false;
	// }
	// // 扣减库存
	// productDao.decreaseProduct(productId, quantity);
	// // 初始化购买记录
	// PurchaseRecordPo pr = this.initPurchaseRecord(userId, product, quantity);
	// // 插入购买记录
	// purchaseRecordDao.insertPurchaseRecord(pr);
	// return true;
	// }

	// @Override
	// // 启动Spring数据库事务机制
	// @Transactional
	// public boolean purchase(Long userId, Long productId, int quantity) {
	// // 获取产品（线程旧值）
	// ProductPo product = productDao.getProduct(productId);
	// // 比较库存和购买数量
	// if (product.getStock() < quantity) {
	// // 库存不足
	// return false;
	// }
	// // 获取当前版本号
	// int version = product.getVersion();
	// // 扣减库存,同时将当前版本号发送给后台去比较
	// int result = productDao.decreaseProduct(productId, quantity, version);
	// // 如果更新数据失败，说明数据在多线程中被其他线程修改，导致失败返回
	// if (result == 0) {
	// return false;
	// }
	// // 初始化购买记录
	// PurchaseRecordPo pr = this.initPurchaseRecord(userId, product, quantity);
	// // 插入购买记录
	// purchaseRecordDao.insertPurchaseRecord(pr);
	// return true;
	// }

//	@Override
//	// 启动Spring数据库事务机制
//	@Transactional(isolation = Isolation.READ_COMMITTED)
//	public boolean purchase(Long userId, Long productId, int quantity) {
//		// 当前时间
//		long start = System.currentTimeMillis();
//		// 循环尝试直至成功
//		while (true) {
//			// 循环时间
//			long end = System.currentTimeMillis();
//			// 如果循环时间大于100毫秒返回终止循环
//			if (end - start > 100) {
//				return false;
//			}
//			// 获取产品
//			ProductPo product = productDao.getProduct(productId);
//			// 获取当前版本号
//			int version = product.getVersion();
//			// 比较库存和购买数量
//			if (product.getStock() < quantity) {
//				// 库存不足
//				return false;
//			}
//
//			// 扣减库存,同时将当前版本号发送给后台去比较
//			int result = productDao.decreaseProduct(productId, quantity, version);
//			// 如果更新数据失败，说明数据在多线程中被其他线程修改，
//			// 导致失败，则通过循环重入尝试购买商品
//			if (result == 0) {
//				continue;
//			}
//			// 初始化购买记录
//			PurchaseRecordPo pr = this.initPurchaseRecord(userId, product, quantity);
//			// 插入购买记录
//			purchaseRecordDao.insertPurchaseRecord(pr);
//			return true;
//		}
//	}
	
	@Override
	// 启动Spring数据库事务机制，并将隔离级别设置为读写提交
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean purchase(Long userId, Long productId, int quantity) {
	    // 当前时间
	    long start = System.currentTimeMillis();
	    // 循环尝试直至成功
	    while(true) {
	        // 循环时间
	        long end = System.currentTimeMillis();
	        // 如果循环时间大于100毫秒返回终止循环
	        if (end - start > 100) {
	            return false;
	        }
	        // 获取产品
	        ProductPo product = productDao.getProduct(productId);
	        // 比较库存和购买数量
	        if (product.getStock() < quantity) {
	            // 库存不足
	            return false;
	        }
	        // 获取当前版本号
	        int version = product.getVersion();
	        // 扣减库存,同时将当前版本号发送给后台去比较
	        int result = productDao.decreaseProduct(productId, quantity);
	        // 如果更新数据失败，说明数据在多线程中被其他线程修改，
	        // 导致失败，则通过循环重入尝试购买商品
	        if (result == 0) {
	            continue;
	        }
	        // 初始化购买记录
	        PurchaseRecordPo pr = this.initPurchaseRecord(userId, product, quantity);
	        // 插入购买记录
	        purchaseRecordDao.insertPurchaseRecord(pr);
	        return true;
	    }
	}

	
	@Autowired
	StringRedisTemplate stringRedisTemplate = null;
	String purchaseScript = 
			// 先将产品编号保存到集合中
			" redis.call('sadd', KEYS[1], ARGV[2]) \n"
			// 购买列表
			+ "local productPurchaseList = KEYS[2]..ARGV[2] \n"
			// 用户编号
			+ "local userId = ARGV[1] \n"
			// 产品key
			+ "local product = 'product_'..ARGV[2] \n"
			// 购买数量
			+ "local quantity = tonumber(ARGV[3]) \n"
			// 当前库存
			+ "local stock = tonumber(redis.call('hget', product, 'stock')) \n"
			// 价格
			+ "local price = tonumber(redis.call('hget', product, 'price')) \n"
			// 购买时间
			+ "local purchase_date = ARGV[4] \n"
			// 库存不足，返回0
			+ "if stock < quantity then return 0 end \n"
			// 减库存
			+ "stock = stock - quantity \n" 
			+ "redis.call('hset', product, 'stock', tostring(stock)) \n"
			// 计算价格
			+ "local sum = price * quantity \n"
			// 合并购买记录数据
			+ "local purchaseRecord = userId..','..quantity..','"
			+ "..sum..','..price..','..purchase_date \n"
			// 保存到将购买记录保存到list里
			+ "redis.call('rpush', productPurchaseList, purchaseRecord) \n"
			// 返回成功
			+ "return 1 \n";
	// Redis购买记录集合前缀
	private static final String PURCHASE_PRODUCT_LIST = "purchase_list_";
	// 抢购商品集合
	private static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";
	// 32位SHA1编码，第一次执行的时候先让Redis进行缓存脚本返回
	private String sha1 = null;

	@Override
	public boolean purchaseRedis(Long userId, Long productId, int quantity) {
		// 购买时间
		Long purchaseDate = System.currentTimeMillis();
		Jedis jedis = null;
		try {
			// 获取原始连接
			jedis = (Jedis) stringRedisTemplate
				.getConnectionFactory().getConnection().getNativeConnection();
			// 如果没有加载过，则先将脚本加载到Redis服务器，让其返回sha1
			if (sha1 == null) {
				sha1 = jedis.scriptLoad(purchaseScript);
			}
			// 执行脚本，返回结果
			Object res = jedis.evalsha(sha1, 2, PRODUCT_SCHEDULE_SET, 
				PURCHASE_PRODUCT_LIST, userId + "", productId + "", 
				quantity + "", purchaseDate + "");
			Long result = (Long) res;
			return result == 1;
		} finally {
			// 关闭jedis连接
			if (jedis != null && jedis.isConnected()) {
				jedis.close();
			}
		}
	}
	
	// 初始化购买信息
	private PurchaseRecordPo initPurchaseRecord(Long userId, ProductPo product, int quantity) {
		PurchaseRecordPo pr = new PurchaseRecordPo();
		pr.setNote("购买日志，时间：" + System.currentTimeMillis());
		pr.setPrice(product.getPrice());
		pr.setProductId(product.getId());
		pr.setQuantity(quantity);
		double sum = product.getPrice() * quantity;
		pr.setSum(sum);
		pr.setUserId(userId);
		return pr;
	}
	
	@Override
	// 当运行方法启用新的独立事务运行
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean dealRedisPurchase(List<PurchaseRecordPo> prpList) {
	    for (PurchaseRecordPo prp : prpList) {
	        purchaseRecordDao.insertPurchaseRecord(prp);
	        productDao.decreaseProduct(prp.getProductId(), prp.getQuantity());
	    }
	    return true;
	}
}