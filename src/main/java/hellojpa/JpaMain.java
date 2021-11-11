package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-demo-application");

        // 데이터 베이스 커넥션을 하나 받는다고 생각할 수 있다.
        EntityManager em = emf.createEntityManager();

        // jpa 는 트랜잭션의 단위가 중요하다.
        EntityTransaction transaction = em.getTransaction();

        // 트랜잭션 시작.
        transaction.begin();

        try {
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloA");
            em.persist(member);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        // 트랜잭션 종료
        transaction.commit();

        // 자원 회수
        em.close();
        emf.close();
    }
}
