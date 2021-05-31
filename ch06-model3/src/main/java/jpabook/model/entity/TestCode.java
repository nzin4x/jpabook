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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Embeddable
class CodeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7815952941315975374L;

	@Column(name = "grp_id")
	Long codeGrpId;
	
	@Column(name = "cd_id")
	Long codeId; // 복합키는 auto incremental 할 수 없다.

	public CodeId(Long grpId, long id) {
		this.codeGrpId = grpId;
		this.codeId = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeGrpId == null) ? 0 : codeGrpId.hashCode());
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
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
		CodeId other = (CodeId) obj;
		if (codeGrpId == null) {
			if (other.codeGrpId != null)
				return false;
		} else if (!codeGrpId.equals(other.codeGrpId))
			return false;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		return true;
	}

	public Long getCodeGrpId() {
		return codeGrpId;
	}

	public void setCodeGrpId(Long codeGrpId) {
		this.codeGrpId = codeGrpId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

}

@Entity
@Table(name = "code")
class Code {

	@EmbeddedId
	private CodeId id;
	private String cdNm;
	private String desc;
	
	@ManyToOne
	@MapsId("grp_id")
	@JoinColumn(name="grp_id")
	CodeGrp grp;
	public Code(String cdNm, String desc) {
		this.cdNm= cdNm;
		this.desc = desc;
	}
	public CodeId getId() {
		return id;
	}
	public void setId(CodeId id) {
		this.id = id;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public CodeGrp getGrp() {
		return grp;
	}
	public void setGrp(CodeGrp grp) {
		this.grp = grp;
	}

}

@Entity
@Table(name = "code_grp")
class CodeGrp {

	@Id
	@Column(name = "grp_id")
	@GeneratedValue
	private Long id;

	private String grpNm;

	public String getGrpNm() {
		return grpNm;
	}

	public void setGrpNm(String grp) {
		this.grpNm = grp;
	}

	private String desc;

	public CodeGrp() {
	}

	public CodeGrp(String grpNm, String desc) {
		this.grpNm = grpNm;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;

	}

}

public class TestCode {

	private static EntityManager em;
	private static EntityManagerFactory emf;
	private static EntityTransaction tx;

	@AfterClass
	public static void end() {
		emf.close();
	}

	@BeforeClass
	public static void start() {
		emf = Persistence.createEntityManagerFactory("jpabook");

	}

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void testCodeGrp() {
		CodeGrp cdGrp = new CodeGrp("NM", "name");
		em.persist(cdGrp);
	}

	@Test
	public void testCode() {
		CodeGrp grp = new CodeGrp("BNK", "bank");
		em.persist(grp);

		Code child = new Code("KIUP", "Kiup Bank");
		child.setGrp(grp);
		child.setId(new CodeId(grp.getId(), 1L));
		grp.setDesc("new child");

		em.persist(child);

	}

	@After
	public void txEnd() {
		tx.commit();
		em.close();

	}

	@Before
	public void txStart() {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin(); // 트랜잭션 시작
	}

}