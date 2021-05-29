package jpabook.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.hibernate.jpa.criteria.OrderImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jpabook.model.entity.Category;
import jpabook.model.entity.Delivery;
import jpabook.model.entity.DeliveryStatus;
import jpabook.model.entity.Item;
import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.OrderItem;
import jpabook.model.entity.OrderStatus;


@Transactional
public class MainTest {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;

	@BeforeClass
	public static void setup() {
        emf = Persistence.createEntityManagerFactory("jpabook");
        em = emf.createEntityManager();

        tx = em.getTransaction();
        tx.begin(); //트랜잭션 시작
	}
	
	@Test
	public void 카테고리추가() {

            
            Category cat = new Category();
            cat.setName("가방");
            em.persist(cat);

            System.out.println("신규 카테고리");
            System.out.println(cat);
            
            Item item = new Item();
            item.setName("가방1");
            item.setPrice(1000);
            item.setStockQuantity(10);
            em.persist(item);

            System.out.println("신규 아이템");
            System.out.println(item);
            
            cat.addItem(item);

            System.out.println("카테고리 추가 후");
            System.out.println(cat);
            
            Member mem = new Member("전승호");
            em.persist(mem); // 4;
            
            System.out.println("멤버 추가 후");
            System.out.println(mem);
            
            OrderItem oi = new OrderItem();
            oi.setItem(item);
            oi.setOrderPrice(3000);
            em.persist(oi);

            System.out.println("주문내역 추가 후");
            System.out.println(oi);

            
            Order ord = new Order();
            ord.setMember(mem);
            ord.setOrderDate(new Date());
            ord.addOrderItem(oi);
            ord.setStatus(OrderStatus.ORDER);

            Delivery del = new Delivery();
            del.setStreet("집 주소");
            del.setZipcode("13423");
            del.setOrder(ord);
            del.setStatus(DeliveryStatus.READY);
            
            em.persist(ord);
            System.out.println("주문 추가 후");
            System.out.println(ord);

            em.persist(del);
            System.out.println("배송 추가 후");
            System.out.println(del);



	}
	
	@AfterClass
	public static void teardown() {
		// TODO Auto-generated method stub

            // tx.commit();//트랜잭션 커밋
            tx.rollback(); //트랜잭션 롤백
        emf.close(); //엔티티 매니저 팩토리 종료
	}

	

}
