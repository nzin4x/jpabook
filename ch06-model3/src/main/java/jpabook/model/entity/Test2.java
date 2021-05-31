package jpabook.model.entity;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test2 {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void testParent() {
		Parent parent = new Parent("key1", "key3");
		parent.setName("hello Parent 2");
		em.persist(parent);

	}

	@Test
	public void testChild() {
		Parent parent = new Parent("key1", "key2");
		parent.setName("hello Parent");
		em.persist(parent);

		Child child = new Child();
		child.setName("hello child");
		child.setParent(parent);
		em.persist(child);

	}

	@BeforeClass
	public static void start() {
		emf = Persistence.createEntityManagerFactory("jpabook");

	}

	@Before
	public void txStart() {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin(); // 트랜잭션 시작
	}

	@After
	public void txEnd() {
		tx.commit();
		em.close();

	}

	@AfterClass
	public static void end() {
		emf.close();
	}

}

@Entity
@IdClass(ParentId.class)
@Table(name = "parent")
class Parent {

	@Id
	@Column(name = "parent_id1")
	private String id1;
	@Id
	@Column(name = "parent_id2")
	private String id2;
	private String name;

	public Parent() {
	}

	public Parent(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getName() {
		return name;
	}

}

class ParentId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3686026681833446229L;
	private String id1;
	private String id2;

	public ParentId() {
	}

	public ParentId(String id1, String id2) {
		super();
		this.id1 = id1;
		this.id2 = id2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
		result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParentId other = (ParentId) obj;
		if (id1 == null) {
			if (other.id1 != null)
				return false;
		} else if (!id1.equals(other.id1))
			return false;
		if (id2 == null) {
			if (other.id2 != null)
				return false;
		} else if (!id2.equals(other.id2))
			return false;
		return true;
	}

}
