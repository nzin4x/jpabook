package jpabook.model.entity;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
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
		Parent parent = new Parent();
		parent.setId(new ParentId("key 3","key4"));
		parent.setName("hello Parent 34");
		em.persist(parent);

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
@Table(name = "parent")
class Parent {

	@EmbeddedId
	private ParentId id;
	private String name;

	public Parent() {
	}


	public ParentId getId() {
		return id;
	}


	public void setId(ParentId id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

}

@Embeddable
class ParentId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3686026681833446229L;
	@Column(name="parent_id1")
	private String id1;
	@Column(name="parent_id2")
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
